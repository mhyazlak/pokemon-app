import requests

evoNumber = 129
api_base_url = "https://pokeapi.co/api/v2/evolution-chain/"
output_file = "V1_4__INSERT_EVOLUTION_CHAIN.sql"

# Helper function to generate SQL
def generate_sql(pokemon_name, stage, stage_one, stage_two=None, stage_three=None, triggered_by=None):
    stage_two_val = f"'{stage_two}'" if stage_two else 'NULL'
    stage_three_val = f"'{stage_three}'" if stage_three else 'NULL'
    triggered_by_val = f"'{triggered_by}'" if triggered_by else 'NULL'

    return f"INSERT INTO EvolutionChain (pokemon_name, stage, stage_one, stage_two, stage_three, triggered_by) VALUES ('{pokemon_name}', {stage}, '{stage_one}', {stage_two_val}, {stage_three_val}, {triggered_by_val});\n"

with open(output_file, "w") as sql_file:
    for evolution_id in range(1, evoNumber + 1):
        api_url = f"{api_base_url}{evolution_id}"
        response = requests.get(api_url)
        if response.status_code == 200:
            data = response.json()

            first_stage = data["chain"]["species"]["name"]
            sql_file.write(generate_sql(first_stage, 1, first_stage))

            # Handle second stage
            for second_stage_data in data["chain"]["evolves_to"]:
                second_stage = second_stage_data["species"]["name"]
                triggered_by = second_stage_data["evolution_details"][0]["trigger"]["name"]
                sql_file.write(generate_sql(second_stage, 2, first_stage, second_stage, None, triggered_by))

                # Handle third stage
                for third_stage_data in second_stage_data["evolves_to"]:
                    third_stage = third_stage_data["species"]["name"]
                    triggered_by = third_stage_data["evolution_details"][0]["trigger"]["name"]
                    sql_file.write(generate_sql(third_stage, 3, first_stage, second_stage, third_stage, triggered_by))

        else:
            print(f"Failed to fetch data for EvolutionChain with ID {evolution_id}")

print(f"SQL insert statements have been written to {output_file}")
