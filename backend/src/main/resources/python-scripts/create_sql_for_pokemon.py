import requests
import base64

dexNumber = 251
api_base_url = "https://pokeapi.co/api/v2/pokemon/"
output_file = "V1_100__INSERTING_POKEMON_DATA.sql"

with open(output_file, "w") as sql_file:
    for pokemon_id in range(1, dexNumber + 1):
        api_url = f"{api_base_url}{pokemon_id}"
        response = requests.get(api_url)

        if response.status_code == 200:
            data = response.json()
            pokemon_name = data["name"]

            # Front sprite
            pokemon_front_sprite_url = data["sprites"]["other"]["official-artwork"]["front_default"]

            # Back sprite
            pokemon_back_sprite_url = data["sprites"]["back_default"]

            # Icon (base64 encoding)
            pokemon_icon_url = data["sprites"]["front_default"]
            icon_response = requests.get(pokemon_icon_url)
            if icon_response.status_code == 200:
                pokemon_icon_b64 = base64.b64encode(icon_response.content).decode('utf-8')
            else:
                pokemon_icon_b64 = 'NULL'

            # Types
            pokemon_type_one = f"'{data['types'][0]['type']['name'].upper()}'"
            pokemon_type_two = f"'{data['types'][1]['type']['name'].upper()}'" if len(data["types"]) > 1 else "NULL"

            # Base stats
            pokemon_base_hp = data["stats"][0]["base_stat"]
            pokemon_base_atk = data["stats"][1]["base_stat"]
            pokemon_base_def = data["stats"][2]["base_stat"]
            pokemon_base_spa = data["stats"][3]["base_stat"]
            pokemon_base_spd = data["stats"][4]["base_stat"]
            pokemon_base_spe = data["stats"][5]["base_stat"]

            # SQL insert statement
            sql_statement = f"INSERT INTO Pokemon (id, name, type_one, type_two, base_hp, base_atk, base_def, base_spa, base_spd, base_speed, front_sprite, back_sprite, icon_b64) VALUES ({pokemon_id}, '{pokemon_name}', {pokemon_type_one}, {pokemon_type_two}, {pokemon_base_hp}, {pokemon_base_atk}, {pokemon_base_def}, {pokemon_base_spa}, {pokemon_base_spd}, {pokemon_base_spe}, '{pokemon_front_sprite_url}', '{pokemon_back_sprite_url}', '{pokemon_icon_b64}');\n"
            sql_file.write(sql_statement)
        else:
            print(f"Failed to fetch data for Pokemon with ID {pokemon_id}")

print(f"SQL insert statements have been written to {output_file}")
