CREATE VIEW POKEMON_VIEW AS
    SELECT
        p.*,
        (p.base_hp + p.base_atk + p.base_def + p.base_spa + p.base_spd + p.base_spe) as base_total,
        ec.stage
    FROM
        Pokemon p
    LEFT JOIN
        EvolutionChain ec ON p.name = ec.pokemon_name;

CREATE VIEW TYPE_STATS_VIEW AS
	SELECT
        AVG(base_hp) as avg_base_hp,
        AVG(base_atk) as avg_base_atk,
        AVG(base_def) as avg_base_def,
        AVG(base_spa) as avg_base_spa,
        AVG(base_spd) as avg_base_spd,
        AVG(base_spe) as avg_base_spe,
        type_one
    from
        Pokemon p
    group by
        type_one;

CREATE VIEW FAMILY_TREE_VIEW AS
    SELECT
        p.id,
        p.name,
        p.type_one ,
        p.type_two,
        p.icon_b64,
        ec.triggered_by,
        ec.stage,
        ec2.pokemon_name as target_pokemon,
        tp.id as target_id
    FROM
        Pokemon p
    LEFT JOIN EvolutionChain ec ON
        p.name = ec.pokemon_name
    JOIN EvolutionChain ec2 ON
        ec.stage_one = ec2.stage_one
    JOIN Pokemon tp ON
        ec2.pokemon_name = tp.name;