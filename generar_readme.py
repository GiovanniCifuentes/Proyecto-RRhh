import os
import re

def extract_docstrings(file_path):
    """Extrae docstrings de archivos Python para documentar automáticamente."""
    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()
    docstrings = re.findall(r'"""(.*?)"""', content, re.DOTALL)
    return "\n".join(docstrings)

repo_name = os.path.basename(os.getcwd())
readme_content = f"# {repo_name}\n\n## Descripción\nEste repositorio contiene el código fuente de {repo_name}.\n\n## Archivos y Documentación\n"

for file in os.listdir():
    if file.endswith(".py") and file != "README.md":
        readme_content += f"\n### {file}\n"
        readme_content += extract_docstrings(file) or "No hay documentación disponible."

with open("README.md", "w") as f:
    f.write(readme_content)

print("README.md generado con éxito.")
