CREATE VIEW POKEMON_VIEW AS
    SELECT
        p.*,
        (p.base_hp + p.base_atk + p.base_def + p.base_spa + p.base_spd + p.base_speed) as base_total,
        IFNULL(ec.stage, 1) AS stage
    FROM
        Pokemon p
    LEFT JOIN
        EvolutionChain ec ON p.id = ec.pokemon_id;


CREATE VIEW MOVESET_VIEW AS
    SELECT
        mo.id move_id,
        mo.name name,
		mo.`type` 'type',
		mo.`power` 'power',
		mo.accuracy,
		mo.damage_class,
		tm.id member_id
	FROM
		TeamMember tm
		JOIN Moveset m ON
			tm.id = m.member_id
		JOIN Move mo ON
			m.move_id = mo.id;

CREATE VIEW TEAMMEMBER_VIEW AS
    SELECT
        tm.id,
        tm.team_id,
        tm.pokemon_id,
        tm.iv_hp,
        tm.iv_atk,
        tm.iv_def,
        tm.iv_spa,
        tm.iv_spd,
        tm.iv_speed,
        tm.ev_hp,
        tm.ev_atk,
        tm.ev_def,
        tm.ev_spa,
        tm.ev_spd,
        tm.ev_speed,
        tm.max_hp,
        tm.calc_atk,
        tm.calc_def,
        tm.calc_spa,
        tm.calc_spd,
        tm.calc_speed,
        tm.selected,
        tm.valid,
        tm.nature_id,
        n.name as nature_name
    FROM
		TeamMember tm
		join Nature n ON
			tm.nature_id = n.id;
