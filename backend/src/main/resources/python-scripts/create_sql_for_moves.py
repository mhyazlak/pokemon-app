import requests
import base64

# Define the total number of moves to fetch. You might need to adjust this number based on the API's data.
total_moves = 919  # total number of moves in pokemon
api_base_url = "https://pokeapi.co/api/v2/move/"
output_file = "V1_102__INSERTING_MOVE_DATA.sql"

# Create or open the SQL file for writing
with open(output_file, "w") as sql_file:
    for move_id in range(1, total_moves + 1):
        api_url = f"{api_base_url}{move_id}"
        response = requests.get(api_url)

        if response.status_code == 200:
            data = response.json()
            move_name = data["name"]
            move_type = data["type"]["name"].upper()  # Capitalizing the move type
            move_power = data.get("power", "NULL")  # Some moves might not have a power value
            move_accuracy = data.get("accuracy", "NULL")  # Some moves might not have an accuracy value
            move_dmg_class = data["damage_class"]["name"].upper()

            # Write the SQL insert statement with all columns to the file
            sql_statement = f"INSERT INTO Move (name, type, power, accuracy, damage_class) VALUES ('{move_name}', '{move_type}', {move_power}, {move_accuracy}, '{move_dmg_class}');\n"
            sql_file.write(sql_statement)
            print(f"{move_id}")
        else:
            print(f"Failed to fetch data for Move with ID {move_id}")

print(f"SQL insert statements have been written to {output_file}")
