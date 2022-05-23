
/* ===============+++++++++++++=========== */
DROP DATABASE IF EXISTS ww2planes_db;
CREATE DATABASE ww2planes_db;
/* ====================+++++++++++++====== */

USE ww2planes_db;

/* set time zone: =====================================*/
SET GLOBAL time_zone = '+1:00';

/* throw custom error: */
DELIMITER $$
CREATE PROCEDURE throw_error (IN message VARCHAR(256))
BEGIN
	SIGNAL SQLSTATE 'ERR0R'
	SET MESSAGE_TEXT = message;
END $$
DELIMITER ;
/*https://stackoverflow.com/questions/4862911/how-to-throw-an-error-in-mysql-procedure */

/*----------------------------------------------------*/
/* years covered */

CREATE TABLE years (
	yearID INT NOT NULL AUTO_INCREMENT,
	year_value INT(4) DEFAULT NULL,
	PRIMARY KEY (yearID),
	UNIQUE (year_value) /* prevent duplicate inserts */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_year (IN year_value INT(4))
BEGIN
	INSERT IGNORE INTO years (year_value) VALUES (year_value);
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* historical periods (eg: early 1940) */

CREATE TABLE periods ( 
	periodID INT NOT NULL AUTO_INCREMENT,
	block ENUM ('Early','Mid','Late') NOT NULL,
	yearID INT,
	PRIMARY KEY (periodID),
	FOREIGN KEY (yearID) REFERENCES years(yearID),
	CONSTRAINT block_yearID UNIQUE (block, yearID)	/* make combined columns unique */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_period (IN block_option VARCHAR(5), IN year_value INT(4))
BEGIN
	DECLARE periodID_check INT DEFAULT 0;
	
	/* insert year_value to years if not present; */
	CALL insert_year (year_value);
	
	/* check for periodID relating to block_option & year_value: */
	SELECT periodID INTO periodID_check FROM periods
		INNER JOIN years ON periods.yearID = years.yearID
	WHERE periods.block = block_option AND years.year_value = year_value;
	
	IF periodID_check = 0 THEN /* insert entry if periodID not found: */
		/* error thrown here if block_option doesnt match enum options: */ 
		INSERT INTO periods (block, yearID) VALUES ( 
			block_option,(SELECT yearID FROM years WHERE years.year_value = year_value));
	END IF;
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* airforces involved */

CREATE TABLE airforces( 
	airforceID INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(64) DEFAULT NULL,
	PRIMARY KEY (airforceID),
	UNIQUE (name) /* prevent duplicate inserts */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_airforce (IN airforce_name VARCHAR(64))
BEGIN
	/* error thrown here on duplicate airforce_name insert: */ 
	INSERT INTO airforces (name) VALUES (airforce_name); 
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* planes available */

CREATE TABLE planes(
	planeID INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(64) DEFAULT NULL,
	type ENUM ('Fighter','Bomber','Fighter-bomber') NOT NULL,
	speed INT DEFAULT 0,
	PRIMARY KEY (planeID),
	UNIQUE (name) /* prevent duplicate inserts */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_plane (IN plane_name VARCHAR(64), IN plane_type VARCHAR(14), IN plane_speed INT)
BEGIN
	INSERT IGNORE INTO planes (name, type, speed) VALUES (
		plane_name, plane_type, plane_speed); 
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* planes available to each airforce */

CREATE TABLE airforce_planes(
	airforce_planeID INT NOT NULL AUTO_INCREMENT,
	airforceID INT,
	planeID INT,
	PRIMARY KEY (airforce_planeID),
	FOREIGN KEY (airforceID) REFERENCES airforces(airforceID),
	FOREIGN KEY (planeID) REFERENCES planes(planeID),
	CONSTRAINT airforceID_planeID UNIQUE (airforceID, planeID)	/* make combined columns unique */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_airforce_plane (IN airforce_name VARCHAR(64), IN plane_name VARCHAR(64), 
IN plane_type VARCHAR(14), IN plane_speed INT)
BEGIN
	/* insert plane to planes if not present: */
	CALL insert_plane (plane_name, plane_type, plane_speed);
	
	/* error thrown here on duplicate airforce_plane insert: */ 
	INSERT INTO airforce_planes (airforceID, planeID) VALUES ( 
		(SELECT airforceID FROM airforces WHERE airforces.name = airforce_name),
		(SELECT planeID FROM planes WHERE planes.name = plane_name));
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE select_airforce_planes (IN airforce_ID INT)
BEGIN
	SELECT
		planes.name AS plane_name,
		planes.type AS plane_type,
		planes.speed AS plane_speed,
		airforce_planes.airforce_planeID AS airforce_plane_ID
	FROM airforce_planes
		INNER JOIN planes ON airforce_planes.planeID = planes.planeID
	WHERE airforce_planes.airforceID = airforce_ID;
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* availability statuses of air force planes in relation to historical periods */

CREATE TABLE plane_availabilities(
	plane_availabilityID INT NOT NULL AUTO_INCREMENT,
	airforce_planeID INT,
	periodID INT,
	status ENUM ('Unavailable','Limited','Common') NOT NULL DEFAULT 'Unavailable',
	PRIMARY KEY (plane_availabilityID),
	FOREIGN KEY (airforce_planeID) REFERENCES airforce_planes(airforce_planeID),
	FOREIGN KEY (periodID) REFERENCES periods(periodID),
	CONSTRAINT airforce_planeID_periodID UNIQUE (airforce_planeID, periodID)	/* make combined columns unique */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_plane_availability (IN airforce_name VARCHAR(64), IN plane_name VARCHAR(64),
IN block_option VARCHAR(5), IN year_value INT(4), IN status_option VARCHAR(11))
BEGIN
	/* insert period to periods if not present: */
	CALL insert_period (block_option, year_value);
	
	/* error thrown here if status_option doesnt match enum options, 
	or on an attempt to insert identical periods for the same airforce_plane: */
	
	INSERT INTO plane_availabilities (airforce_planeID, periodID, status) VALUES ( 
		(SELECT airforce_planeID FROM airforce_planes
			INNER JOIN airforces ON airforce_planes.airforceID = airforces.airforceID
			INNER JOIN planes ON airforce_planes.planeID = planes.planeID
		WHERE airforces.name = airforce_name AND planes.name = plane_name),
		(SELECT periodID FROM periods
			INNER JOIN years ON periods.yearID = years.yearID
		WHERE block = block_option AND years.year_value = year_value),
		status_option);
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE select_plane_availabilities (IN airforce_plane_ID INT, IN event_ID INT)
BEGIN
	SELECT
		periods.block AS block_option,
		years.year_value AS year_value,
		plane_availabilities.status AS status_option
	FROM plane_availabilities
		INNER JOIN periods ON plane_availabilities.periodID = periods.periodID
		INNER JOIN years ON periods.yearID = years.yearID
		INNER JOIN event_periods ON plane_availabilities.periodID = event_periods.periodID
	WHERE plane_availabilities.airforce_planeID = airforce_plane_ID
		AND event_periods.eventID = event_ID;
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* historical events */

CREATE TABLE events( 
	eventID INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(64) DEFAULT NULL,
	PRIMARY KEY (eventID),
	UNIQUE (name) /* prevent duplicate inserts */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_event (IN event_name VARCHAR(64))
BEGIN
	/* error thrown here on duplicate event_name insert: */ 
	INSERT INTO events (name) VALUES (event_name);
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE select_events ()
BEGIN
	SELECT
		events.name AS event_name,
		events.eventID AS event_ID
	FROM events; 
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* airforces available to events */

CREATE TABLE event_airforces (
	event_airforceID INT NOT NULL AUTO_INCREMENT,
	eventID INT,
	airforceID INT,
	PRIMARY KEY (event_airforceID),
	FOREIGN KEY (eventID) REFERENCES events(eventID),
	FOREIGN KEY (airforceID) REFERENCES airforces(airforceID),
	CONSTRAINT eventID_airforceID UNIQUE (eventID, airforceID)	/* make combined columns unique */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_event_airforce (IN event_name VARCHAR(64), IN airforce_name VARCHAR(64))
BEGIN
	/* error thrown here on duplicate event_airforce insert: */
	INSERT INTO event_airforces (eventID, airforceID) VALUES ( 
		(SELECT eventID FROM events WHERE events.name = event_name),
		(SELECT airforceID FROM airforces WHERE airforces.name = airforce_name));
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE select_event_airforces (IN event_ID INT)
BEGIN
	SELECT
		airforces.name AS airforce_name, 
		event_airforces.airforceID AS airforce_ID
	FROM event_airforces
		INNER JOIN airforces ON event_airforces.airforceID = airforces.airforceID
	WHERE event_airforces.eventID = event_ID;
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* periods available to events */

CREATE TABLE event_periods (
	event_periodID INT NOT NULL AUTO_INCREMENT,
	eventID INT, 
	periodID INT,
	PRIMARY KEY (event_periodID),
	FOREIGN KEY (eventID) REFERENCES events(eventID),
	FOREIGN KEY (periodID) REFERENCES periods(periodID),
	CONSTRAINT eventID_periodID UNIQUE (eventID, periodID) /* make combined columns unique */
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DELIMITER $$
CREATE PROCEDURE insert_event_period (IN event_name VARCHAR(64), IN block_start VARCHAR(5), 
IN year_start INT(4), IN block_end VARCHAR(5), IN year_end INT(4))
BEGIN
	DECLARE curr_year INT DEFAULT year_start; /* holds year values */
	DECLARE curr_block INT; /* holds block values */
	DECLARE can_add BOOLEAN DEFAULT FALSE; /* flag for adding values */
	
	/* enum index of block_start: */
	DECLARE block_start_index INT DEFAULT (
		SELECT periods.block+0 FROM periods 
			INNER JOIN years ON periods.yearID = years.yearID
		WHERE periods.block = block_start AND years.year_value = year_start);
	
	/* enum index of block_end: */
	DECLARE block_end_index INT DEFAULT	(
		SELECT periods.block+0 FROM periods
			INNER JOIN years ON periods.yearID = years.yearID
		WHERE periods.block = block_end AND years.year_value = year_end);
		
	/* max enum index of block: */
	DECLARE block_max_index INT DEFAULT (
		SELECT MAX(periods.block+0) FROM periods);
		
	/* check that start year value isnt later than end year value: */
	IF year_start > year_end
		THEN CALL throw_error("insert_event_period: [year_start] > [year_end]");
	END IF;
	
	/* if years are the same: */
	IF year_start = year_end THEN
	
		/* check that block_start_index isnt later than block_end_index: */
		IF block_start_index > block_end_index
			THEN CALL throw_error("insert_event_period: [block_start] > [block_end]");
		END IF;
		
		/* check that both enum indexes arent the same: */
		IF block_start_index = block_end_index
			THEN CALL throw_error(
				"insert_event_period: [block_start, year_start] = [block_end, year_end]");
		END IF;
	END IF;
	
	/* add event_periods: */
	outer_while:
	WHILE curr_year <= year_end DO /* loop through years */
	
		SET curr_block = 1; /* (re)set current block */
		WHILE curr_block <= block_max_index DO /* loop through blocks */
			
			/* if found start date, allow adding of event periods: */
			IF curr_block = block_start_index AND curr_year = year_start THEN
				SET can_add = TRUE;
			END IF;
			
			IF can_add = TRUE THEN
			/* add event period to event_periods: */
			INSERT INTO event_periods (eventID, periodID) VALUES ( 
				(SELECT eventID FROM events WHERE events.name = event_name),
				(SELECT periodID FROM periods 
					INNER JOIN years ON periods.yearID  = years.yearID
				WHERE periods.block = curr_block AND years.year_value = curr_year));
				
				/* stop when final target event_period is added: */
				IF curr_block = block_end_index AND curr_year = year_end THEN
					LEAVE outer_while; /* leave outer while */
				END IF;
			END IF;
			SET curr_block = curr_block + 1; /* move to next block */
		END WHILE;
		SET curr_year = curr_year + 1; /* move to next year */
	END WHILE;
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE select_event_periods (IN event_ID INT)
BEGIN
	SELECT
		periods.block AS period_block,
		years.year_value AS period_year
	FROM event_periods
		INNER JOIN periods ON event_periods.periodID = periods.periodID
		INNER JOIN years ON periods.yearID = years.yearID
	WHERE event_periods.eventID = event_ID
	ORDER BY event_periods.event_periodID ASC;
END $$
DELIMITER ;

/*----------------------------------------------------*/
/* select all entries: */

DELIMITER $$
CREATE PROCEDURE select_all (IN table_name VARCHAR(64))
BEGIN
	/* create query string: */
	SET @query = CONCAT('SELECT * FROM ', table_name, ';'); 
	
	/* prepare and execute statement: */
	PREPARE statement FROM @query;
	EXECUTE statement;
	DEALLOCATE PREPARE statement;
END $$
DELIMITER ;
/* https://stackoverflow.com/questions/27542617/dynamic-table-name-at-sql-statement */

/*----------------------------------------------------*/