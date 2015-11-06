CREATE TABLE FACING (
  FACING_ID BIGSERIAL NOT NULL,
  NAME VARCHAR(10) NOT NULL
);

CREATE TABLE MOVEMENT_DIRECTION (
  MOVEMENT_DIRECTION_ID BIGSERIAL NOT NULL,
  NAME VARCHAR(10) NOT NULL
);

CREATE TABLE COLOR (
  COLOR_ID BIGSERIAL NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  RED SMALLINT NOT NULL,
  GREEN SMALLINT NOT NULL,
  BLUE SMALLINT NOT NULL
);

CREATE TABLE AUTHOR (
  AUTHOR_ID BIGSERIAL NOT NULL,
  FIRST_NAME VARCHAR(50) NOT NULL,
  LAST_NAME VARCHAR(50) NOT NULL
);

CREATE TABLE GAME (
  GAME_ID BIGSERIAL NOT NULL,
  AUTHOR_ID BIGINT NOT NULL,
  NAME VARCHAR(100) NOT NULL
);

CREATE TABLE BOARD (
  BOARD_ID BIGSERIAL NOT NULL,
  AUTHOR_ID BIGINT NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  X_DIMENSION SMALLINT NOT NULL,
  Y_DIMENSION SMALLINT NOT NULL
);

CREATE TABLE BOARD_HOLES (
  BOARD_ID BIGINT NOT NULL,
  X SMALLINT NOT NULL,
  Y SMALLINT NOT NULL
);

CREATE TABLE GAME_BOARD (
  GAME_ID BIGINT NOT NULL,
  BOARD_ID BIGINT NOT NULL
);

CREATE TABLE PIECE (
  PIECE_ID BIGSERIAL NOT NULL,
  AUTHOR_ID BIGINT NOT NULL,
  NAME VARCHAR(100) NOT NULL
);

CREATE TABLE GAME_PIECE (
  GAME_ID BIGINT NOT NULL,
  PIECE_ID BIGINT NOT NULL
);

CREATE TABLE GAME_SETUP (
  GAME_ID BIGINT NOT NULL,
  X SMALLINT NOT NULL,
  Y SMALLINT NOT NULL,
  PIECE_ID BIGINT NOT NULL,
  COLOR_ID BIGINT NOT NULL,
  FACING_ID BIGINT NOT NULL
);

CREATE TABLE MOVEMENT_TYPE (
  MOVEMENT_TYPE_ID BIGSERIAL NOT NULL,
  NAME VARCHAR(100) NOT NULL
);

CREATE TABLE MOVEMENT (
  MOVEMENT_ID BIGSERIAL NOT NULL,
  MOVEMENT_TYPE_ID BIGINT NOT NULL,
  AUTHOR_ID BIGINT NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  STEP_MIN SMALLINT NOT NULL DEFAULT 0,
  STEP_MAX SMALLINT NOT NULL DEFAULT 0
);

CREATE TABLE RESTRICTION_TYPE (
  RESTRICTION_TYPE_ID BIGSERIAL NOT NULL,
  NAME VARCHAR(100) NOT NULL
);

CREATE TABLE RESTRICTION_VALUE_TYPE (
  RESTRICTION_VALUE_TYPE_ID BIGSERIAL NOT NULL,
  NAME VARCHAR(100) NOT NULL
);

CREATE TABLE RESTRICTION (
  RESTRICTION_ID BIGSERIAL NOT NULL,
  AUTHOR_ID BIGINT NOT NULL,
  RESTRICTION_TYPE_ID BIGINT NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  RESTRICTION_VALUE_TYPE_ID BIGINT NOT NULL,
  VALUE BIGINT NOT NULL
);

CREATE TABLE MOVEMENT_RESTRICTION (
  MOVEMENT_ID BIGINT NOT NULL,
  RESTRICTION_ID BIGINT NOT NULL
);

CREATE TABLE CAPTURE_RESTRICTION (
  MOVEMENT_ID BIGINT NOT NULL,
  RESTRICTION_ID BIGINT NOT NULL
);

CREATE TABLE MOVEMENT_COMPONENT (
  MOVEMENT_COMPONENT_ID BIGSERIAL NOT NULL,
  MOVEMENT_DIRECTION_ID BIGINT NOT NULL,
  STEP_SIZE SMALLINT NOT NULL
);

CREATE TABLE MOVEMENT_MOVEMENT_COMPONENT (
  MOVEMENT_ID BIGINT NOT NULL,
  MOVEMENT_COMPONENT_ID BIGINT NOT NULL
);

CREATE TABLE PIECE_MOVEMENT (
  PIECE_ID BIGINT NOT NULL,
  MOVEMENT_ID BIGINT NOT NULL
);

CREATE TABLE PIECE_CAPTURE (
  PIECE_ID BIGINT NOT NULL,
  MOVEMENT_ID BIGINT NOT NULL
);

CREATE TABLE CASTLE_RULE (
  CASTLE_RULE_ID BIGSERIAL NOT NULL,
  NAME VARCHAR(100) NOT NULL
);

CREATE TABLE GAME_CASTLE_RULE (
  GAME_ID BIGINT NOT NULL,
  CASTLE_RULE_ID BIGINT NOT NULL
);