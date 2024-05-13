/*CREATE VIEW TYPE_STATS_VIEW AS
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
        p.type_one,
        p.type_two,
        p.icon_b64,
        ec.triggered_by,
        ec.stage,
        ec2.pokemon_id as target_id,
        tp.id as target_id
    FROM
        Pokemon p
    LEFT JOIN EvolutionChain ec ON
        p.id = ec.pokemon_id
    JOIN EvolutionChain ec2 ON
        ec.stage_one_id = ec2.stage_one_id
    JOIN Pokemon tp ON
        ec2.pokemon_id = tp.id;
*/