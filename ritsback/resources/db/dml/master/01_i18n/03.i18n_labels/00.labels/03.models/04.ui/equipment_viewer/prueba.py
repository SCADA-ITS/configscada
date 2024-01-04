# La lista original
data = [
]

# Ordenar la lista por el cuarto campo
sorted_data = sorted(data, key=lambda x: x[2])

# Mostrar la lista ordenada
for item in sorted_data:
   print(str(item).replace("''CURRENT_TIMESTAMP''", "'CURRENT_TIMESTAMP'") + ",")
   # print(", ".join(map(str, item)))
