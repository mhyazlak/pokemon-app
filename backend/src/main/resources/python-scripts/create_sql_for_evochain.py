import requests

api_base_url = "https://pokeapi.co/api/v2/evolution-chain/"
pokemon_base_url = "https://pokeapi.co/api/v2/pokemon/"
output_file = "V1_101__INSERTING_EVOLUTION_CHAIN_DATA.sql"

def get_pokemon_id(pokemon_name):
    response = requests.get(f"{pokemon_base_url}{pokemon_name}")
    if response.status_code == 200:
        data = response.json()
        pokemon_id = data["id"]
        if pokemon_id > 251:
            return None  # Skip any Pokemon ID above 252
        return pokemon_id
    else:
        print(f"Failed to fetch data for Pokémon {pokemon_name}")
        return None

def generate_sql(pokemon_id, stage, stage_one_id, stage_two_id=None, stage_three_id=None, triggered_by=None):
    stage_two_val = stage_two_id if stage_two_id else 'NULL'
    stage_three_val = stage_three_id if stage_three_id else 'NULL'
    triggered_by_val = f"'{triggered_by}'" if triggered_by else 'NULL'

    return f"INSERT INTO EvolutionChain (pokemon_id, stage, stage_one_id, stage_two_id, stage_three_id, triggered_by) VALUES ({pokemon_id}, {stage}, {stage_one_id}, {stage_two_val}, {stage_three_val}, {triggered_by_val});\n"

evolution_id = 1

with open(output_file, "w") as sql_file:
    while evolution_id <= 252:  # Limit the loop to evolution IDs up to 252
        api_url = f"{api_base_url}{evolution_id}"
        response = requests.get(api_url)
        if response.status_code == 200:
            data = response.json()

            first_stage_name = data["chain"]["species"]["name"]
            first_stage_id = get_pokemon_id(first_stage_name)
            if first_stage_id:
                sql_file.write(generate_sql(first_stage_id, 1, first_stage_id))

                for second_stage_data in data["chain"]["evolves_to"]:
                    second_stage_name = second_stage_data["species"]["name"]
                    second_stage_id = get_pokemon_id(second_stage_name)
                    triggered_by = second_stage_data["evolution_details"][0]["trigger"]["name"]
                    if second_stage_id:
                        sql_file.write(generate_sql(second_stage_id, 2, first_stage_id, second_stage_id, None, triggered_by))

                        for third_stage_data in second_stage_data["evolves_to"]:
                            third_stage_name = third_stage_data["species"]["name"]
                            third_stage_id = get_pokemon_id(third_stage_name)
                            if third_stage_id:
                                sql_file.write(generate_sql(third_stage_id, 3, first_stage_id, second_stage_id, third_stage_id, triggered_by))
            evolution_id += 1
        else:
            print(f"Failed to fetch data for EvolutionChain with ID {evolution_id}")
            break  # Exit the loop if there is a failed HTTP response

print(f"SQL insert statements have been written to {output_file}")
