-- Dropping foreign keys from the EvolutionChain table
#ALTER TABLE EvolutionChain DROP FOREIGN KEY fk_pokemon_evo_chain;
#ALTER TABLE EvolutionChain DROP FOREIGN KEY fk_stage_one_evo_chain;
#ALTER TABLE EvolutionChain DROP FOREIGN KEY fk_stage_two_evo_chain;
#ALTER TABLE EvolutionChain DROP FOREIGN KEY fk_stage_three_evo_chain;

-- Dropping foreign keys from the Team table
#ALTER TABLE Team DROP FOREIGN KEY fk_user_team;

-- Dropping foreign keys from the TeamMember table
#ALTER TABLE TeamMember DROP FOREIGN KEY fk_team_teammember;
#ALTER TABLE TeamMember DROP FOREIGN KEY fk_pokemon_teammember;
