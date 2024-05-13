# Script planning

## V1_000__DELETE.sql
This file drops all Constraints so Tables can be removed and recreated if needed.

## V1_001__OBJECT_TABLES.sql
This file has all the definitions for the entity tables in this project. Some entities will have columns to track their changes and have a history table, they will be marked with A and H
These are the entities in this project:

- User (A, H)
- Authority (A, H)
- Pokemon
- Move
- EvolutionChain
- Team (A, H)
- TeamMember (A, H)
- Moveset (H)
- Gamestate (H) 
- Match (H)
- PlayerActions (H)

## V1_002__CREATE_UPDATE_DELETE_STORED_PROCEDURES.sql
This file contains definitions of stored prodecures to Data Manipulation Language.
These Tables will be excluded:

- Pokemon
- Move
- EvolutionCHain

## V1_003__CUSTOM_STORED_PROCEDURES.sql
This file contains definitons of stored procedures other than the DML stored procedures.

## V1_004__CUSTOM_FUNCTIONS.sql
This file contains definitons of functions

## V1_005__ENTITY_VIEWS.sql
This file contains definitions of views that will be used to map the information to the DTO.

## V1_006__CUSTOM_VIEWS.sql
This file contains definitons of views other than entity views.

## V1_100__INSERTING_POKEMON_DATA.sql
This file contains INSERT statements for Pokemon data. This data will never be changed and will be inserted by INSERT instead of using a SP.

## V1_101__INSERTING_EVOLUTION_CHAIN_DATA.sql
This file contains INSERT statements for Evolutionchains data. This data will never be changed and will be inserted by INSERT instead of using a SP.

## V1_102__INSERTING_MOVE_DATA.sql
This file contains INSERT statements for Move data. This data will never be changed and will be inserted by INSERT instead of using a SP.

## V1_103__INSERTING_USER_DATA.sql
This file contains insertion of users for development.

## V1_104__INSERTING_AUTHORITY_DATA.sql
This file contains insertion of authorities for users for development.