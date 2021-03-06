ALTER TABLE FACING ADD CONSTRAINT FACING_PK PRIMARY KEY (FACING_ID);
ALTER TABLE MOVEMENT_DIRECTION ADD CONSTRAINT MOVEMENT_DIRECTION_PK PRIMARY KEY (MOVEMENT_DIRECTION_ID);
ALTER TABLE COLOR ADD CONSTRAINT COLOR_PK PRIMARY KEY (COLOR_ID);
ALTER TABLE AUTHOR ADD CONSTRAINT AUTHOR_PK PRIMARY KEY (AUTHOR_ID);
ALTER TABLE GAME ADD CONSTRAINT GAME_PK PRIMARY KEY (GAME_ID);
ALTER TABLE BOARD ADD CONSTRAINT BOARD_PK PRIMARY KEY (BOARD_ID);
ALTER TABLE BOARD_HOLES ADD CONSTRAINT BOARD_HOLES_PK PRIMARY KEY (BOARD_ID, X, Y);
ALTER TABLE GAME_BOARD ADD CONSTRAINT GAME_BOARD_PK PRIMARY KEY (GAME_ID, BOARD_ID);
ALTER TABLE PIECE ADD CONSTRAINT PIECE_PK PRIMARY KEY (PIECE_ID);
ALTER TABLE GAME_PIECE ADD CONSTRAINT GAME_PIECE_PK PRIMARY KEY (GAME_ID, PIECE_ID);
ALTER TABLE GAME_SETUP ADD CONSTRAINT GAME_SETUP_PK PRIMARY KEY (GAME_ID, X, Y);
ALTER TABLE MOVEMENT_TYPE ADD CONSTRAINT MOVEMENT_TYPE_PK PRIMARY KEY (MOVEMENT_TYPE_ID);
ALTER TABLE MOVEMENT ADD CONSTRAINT MOVEMENT_PK PRIMARY KEY (MOVEMENT_ID);
ALTER TABLE RESTRICTION_TYPE ADD CONSTRAINT RESTRICTION_TYPE_PK PRIMARY KEY (RESTRICTION_TYPE_ID);
ALTER TABLE RESTRICTION_VALUE_TYPE ADD CONSTRAINT RESTRICTION_VALUE_TYPE_PK PRIMARY KEY (RESTRICTION_VALUE_TYPE_ID);
ALTER TABLE RESTRICTION ADD CONSTRAINT RESTRICTION_PK PRIMARY KEY (RESTRICTION_ID);
ALTER TABLE MOVEMENT_RESTRICTION ADD CONSTRAINT MOVEMENT_RESTRICTION_PK PRIMARY KEY (MOVEMENT_ID, RESTRICTION_ID);
ALTER TABLE CAPTURE_RESTRICTION ADD CONSTRAINT CAPTURE_RESTRICTION_PK PRIMARY KEY (MOVEMENT_ID, RESTRICTION_ID);
ALTER TABLE MOVEMENT_COMPONENT ADD CONSTRAINT MOVEMENT_COMPONENT_PK PRIMARY KEY (MOVEMENT_COMPONENT_ID);
ALTER TABLE MOVEMENT_MOVEMENT_COMPONENT ADD CONSTRAINT MOVEMENT_MOVEMENT_COMPONENT_PK PRIMARY KEY (MOVEMENT_ID, MOVEMENT_COMPONENT_ID);
ALTER TABLE PIECE_MOVEMENT ADD CONSTRAINT PIECE_MOVEMENT_PK PRIMARY KEY (PIECE_ID, MOVEMENT_ID);
ALTER TABLE PIECE_CAPTURE ADD CONSTRAINT PIECE_CAPTURE_PK PRIMARY KEY (PIECE_ID, MOVEMENT_ID);
ALTER TABLE CASTLE_RULE ADD CONSTRAINT CASTLE_RULE_PK PRIMARY KEY (CASTLE_RULE_ID);
ALTER TABLE GAME_CASTLE_RULE ADD CONSTRAINT GAME_CASTLE_RULE_PK PRIMARY KEY (GAME_ID, CASTLE_RULE_ID);

ALTER TABLE FACING ADD CONSTRAINT FACING_NAME_KEY UNIQUE (NAME);
ALTER TABLE MOVEMENT_DIRECTION ADD CONSTRAINT MOVEMENT_DIRECTION_NAME_KEY UNIQUE (NAME);
ALTER TABLE COLOR ADD CONSTRAINT COLOR_NAME_KEY UNIQUE (NAME);
ALTER TABLE AUTHOR ADD CONSTRAINT AUTHOR_FIRST_NAME_LAST_NAME_KEY UNIQUE (FIRST_NAME, LAST_NAME);
ALTER TABLE GAME ADD CONSTRAINT GAME_AUTHOR_ID_NAME_KEY UNIQUE (AUTHOR_ID, NAME);
ALTER TABLE BOARD ADD CONSTRAINT BOARD_AUTHOR_ID_NAME_KEY UNIQUE (AUTHOR_ID, NAME);
ALTER TABLE PIECE ADD CONSTRAINT PIECE_AUTHOR_ID_NAME_KEY UNIQUE (AUTHOR_ID, NAME);
ALTER TABLE MOVEMENT_TYPE ADD CONSTRAINT MOVEMENT_TYPE_NAME_KEY UNIQUE (NAME);
ALTER TABLE MOVEMENT ADD CONSTRAINT MOVEMENT_AUTHOR_ID_NAME_KEY UNIQUE (AUTHOR_ID, NAME);
ALTER TABLE RESTRICTION_TYPE ADD CONSTRAINT RESTRICTION_TYPE_NAME_KEY UNIQUE (NAME);
ALTER TABLE RESTRICTION_VALUE_TYPE ADD CONSTRAINT RESTRICTION_VALUE_TYPE_NAME_KEY UNIQUE (NAME);
ALTER TABLE RESTRICTION ADD CONSTRAINT RESTRICTION_AUTHOR_ID_NAME_KEY UNIQUE (AUTHOR_ID, NAME);
ALTER TABLE CASTLE_RULE ADD CONSTRAINT CASTLE_RULE_NAME_KEY UNIQUE (NAME);

ALTER TABLE GAME ADD CONSTRAINT GAME_AUTHOR_ID_FKEY FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (AUTHOR_ID);
ALTER TABLE BOARD_HOLES ADD CONSTRAINT BOARD_HOLES_BOARD_ID_FKEY FOREIGN KEY (BOARD_ID) REFERENCES BOARD (BOARD_ID);
ALTER TABLE GAME_BOARD ADD CONSTRAINT GAME_BOARD_GAME_ID_FKEY FOREIGN KEY (GAME_ID) REFERENCES GAME (GAME_ID);
ALTER TABLE GAME_BOARD ADD CONSTRAINT GAME_BOARD_BOARD_ID_FKEY FOREIGN KEY (BOARD_ID) REFERENCES BOARD (BOARD_ID);
ALTER TABLE PIECE ADD CONSTRAINT PIECE_AUTHOR_ID_FKEY FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (AUTHOR_ID);
ALTER TABLE GAME_PIECE ADD CONSTRAINT GAME_PIECE_GAME_ID_FKEY FOREIGN KEY (GAME_ID) REFERENCES GAME (GAME_ID);
ALTER TABLE GAME_PIECE ADD CONSTRAINT GAME_PIECE_PIECE_ID_FKEY FOREIGN KEY (PIECE_ID) REFERENCES PIECE (PIECE_ID);
ALTER TABLE GAME_SETUP ADD CONSTRAINT GAME_SETUP_GAME_ID_FKEY FOREIGN KEY (GAME_ID) REFERENCES GAME (GAME_ID);
ALTER TABLE GAME_SETUP ADD CONSTRAINT GAME_SETUP_PIECE_ID_FKEY FOREIGN KEY (PIECE_ID) REFERENCES PIECE (PIECE_ID);
ALTER TABLE GAME_SETUP ADD CONSTRAINT GAME_SETUP_COLOR_ID_FKEY FOREIGN KEY (COLOR_ID) REFERENCES COLOR (COLOR_ID);
ALTER TABLE GAME_SETUP ADD CONSTRAINT GAME_SETUP_FACING_ID_FKEY FOREIGN KEY (FACING_ID) REFERENCES FACING (FACING_ID);
ALTER TABLE MOVEMENT ADD CONSTRAINT MOVEMENT_AUTHOR_ID_FKEY FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (AUTHOR_ID);
ALTER TABLE MOVEMENT ADD CONSTRAINT MOVEMENT_MOVEMENT_TYPE_ID_FKEY FOREIGN KEY (MOVEMENT_TYPE_ID) REFERENCES MOVEMENT_TYPE (MOVEMENT_TYPE_ID);
ALTER TABLE RESTRICTION ADD CONSTRAINT RESTRICTION_RESTRICTION_TYPE_ID_FKEY FOREIGN KEY (RESTRICTION_TYPE_ID) REFERENCES RESTRICTION_TYPE (RESTRICTION_TYPE_ID);
ALTER TABLE RESTRICTION ADD CONSTRAINT RESTRICTION_RESTRICTION_VALUE_TYPE_ID_FKEY FOREIGN KEY (RESTRICTION_VALUE_TYPE_ID) REFERENCES RESTRICTION_VALUE_TYPE (RESTRICTION_VALUE_TYPE_ID);
ALTER TABLE MOVEMENT_RESTRICTION ADD CONSTRAINT MOVEMENT_RESTRICTION_MOVEMENT_ID_FKEY FOREIGN KEY (MOVEMENT_ID) REFERENCES MOVEMENT (MOVEMENT_ID);
ALTER TABLE MOVEMENT_RESTRICTION ADD CONSTRAINT MOVEMENT_RESTRICTION_RESTRICTION_ID_FKEY FOREIGN KEY (RESTRICTION_ID) REFERENCES RESTRICTION (RESTRICTION_ID);
ALTER TABLE CAPTURE_RESTRICTION ADD CONSTRAINT CAPTURE_RESTRICTION_MOVEMENT_ID_FKEY FOREIGN KEY (MOVEMENT_ID) REFERENCES MOVEMENT (MOVEMENT_ID);
ALTER TABLE CAPTURE_RESTRICTION ADD CONSTRAINT CAPTURE_RESTRICTION_RESTRICTION_ID_FKEY FOREIGN KEY (RESTRICTION_ID) REFERENCES RESTRICTION (RESTRICTION_ID);
ALTER TABLE MOVEMENT_COMPONENT ADD CONSTRAINT MOVEMENT_COMPONENT_MOVEMENT_DIRECTION_ID_FKEY FOREIGN KEY (MOVEMENT_DIRECTION_ID) REFERENCES MOVEMENT_DIRECTION (MOVEMENT_DIRECTION_ID);
ALTER TABLE MOVEMENT_MOVEMENT_COMPONENT ADD CONSTRAINT MOVEMENT_MOVEMENT_COMPONENT_MOVEMENT_ID_FKEY FOREIGN KEY (MOVEMENT_ID) REFERENCES MOVEMENT (MOVEMENT_ID);
ALTER TABLE MOVEMENT_MOVEMENT_COMPONENT ADD CONSTRAINT MOVEMENT_MOVEMENT_COMPONENT_MOVEMENT_COMPONENT_ID_FKEY FOREIGN KEY (MOVEMENT_COMPONENT_ID) REFERENCES MOVEMENT_COMPONENT (MOVEMENT_COMPONENT_ID);
ALTER TABLE PIECE_MOVEMENT ADD CONSTRAINT PIECE_MOVEMENT_PIECE_ID_FKEY FOREIGN KEY (PIECE_ID) REFERENCES PIECE (PIECE_ID);
ALTER TABLE PIECE_MOVEMENT ADD CONSTRAINT PIECE_MOVEMENT_MOVEMENT_ID_FKEY FOREIGN KEY (MOVEMENT_ID) REFERENCES MOVEMENT (MOVEMENT_ID);
ALTER TABLE PIECE_CAPTURE ADD CONSTRAINT PIECE_CAPTURE_PIECE_ID_FKEY FOREIGN KEY (PIECE_ID) REFERENCES PIECE (PIECE_ID);
ALTER TABLE PIECE_CAPTURE ADD CONSTRAINT PIECE_CAPTURE_MOVEMENT_ID_FKEY FOREIGN KEY (MOVEMENT_ID) REFERENCES MOVEMENT (MOVEMENT_ID);
ALTER TABLE GAME_CASTLE_RULE ADD CONSTRAINT GAME_CASTLE_RULE_GAME_ID_FKEY FOREIGN KEY (GAME_ID) REFERENCES GAME (GAME_ID);
ALTER TABLE GAME_CASTLE_RULE ADD CONSTRAINT GAME_CASTLE_RULE_CASTLE_RULE_ID_FKEY FOREIGN KEY (CASTLE_RULE_ID) REFERENCES CASTLE_RULE (CASTLE_RULE_ID);
