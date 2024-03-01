import requests
import base64

dexNumber = 251
api_base_url = "https://pokeapi.co/api/v2/pokemon/"
output_file = "V1_2__INSERT_POKEMON.sql"

# Create or open the SQL file for writing
with open(output_file, "w") as sql_file:
    for pokemon_id in range(1, dexNumber + 1):
        api_url = f"{api_base_url}{pokemon_id}"
        response = requests.get(api_url)

        if response.status_code == 200:
            data = response.json()
            pokemon_name = data["name"]
            pokemon_sprite_url = data["sprites"]["other"]["official-artwork"]["front_default"]
            # download pokemon_icon_b64 and save it as b64 string"

            pokemon_icon_url = data["sprites"]["front_default"]
            icon_response = requests.get(pokemon_icon_url)
            if icon_response.status_code == 200:
                pokemon_icon_b64 = base64.b64encode(icon_response.content).decode('utf-8')



            # Capitalizing the type and handling the possible absence of the second type
            pokemon_type_one = f"'{data['types'][0]['type']['name'].upper()}'"  # wrapped in quotes

            if len(data["types"]) > 1:
                pokemon_type_two = f"'{data['types'][1]['type']['name'].upper()}'"  # wrapped in quotes
            else:
                pokemon_type_two = "NULL"  # no quotes

            pokemon_base_hp = data["stats"][0]["base_stat"]
            pokemon_base_atk = data["stats"][1]["base_stat"]
            pokemon_base_def = data["stats"][2]["base_stat"]
            pokemon_base_spa = data["stats"][3]["base_stat"]
            pokemon_base_spd = data["stats"][4]["base_stat"]
            pokemon_base_spe = data["stats"][5]["base_stat"]

            # Write the SQL insert statement with all columns to the file
            sql_statement = f"INSERT INTO Pokemon (id, name, type_one, type_two, base_hp, base_atk, base_def, base_spa, base_spd, base_spe, sprite, icon_b64) VALUES ({pokemon_id}, '{pokemon_name}', {pokemon_type_one}, {pokemon_type_two}, {pokemon_base_hp}, {pokemon_base_atk}, {pokemon_base_def}, {pokemon_base_spa}, {pokemon_base_spd}, {pokemon_base_spe}, '{pokemon_sprite_url}', '{pokemon_icon_b64}');\n"
            sql_file.write(sql_statement)
        else:
            print(f"Failed to fetch data for Pokemon with ID {pokemon_id}")

print(f"SQL insert statements have been written to {output_file}")
