# 🐾✨ AppMascotas

> Aplicación móvil desarrollada en Android para la gestión de mascotas mediante operaciones CRUD (Crear, Leer, Actualizar y Eliminar) conectada a una API REST.

---

## 📱 🧩 Descripción

**AppMascotas** es una aplicación móvil que permite gestionar información de mascotas de manera eficiente.  
El usuario puede registrar nuevas mascotas, visualizarlas en una lista, editar sus datos y eliminarlas.

La aplicación consume una API REST utilizando la librería **Volley**, permitiendo trabajar con datos en formato JSON en tiempo real.

---

## 🚀 ⚙️ Funcionalidades

- 📋 Listar mascotas desde la API
- ➕ Registrar nuevas mascotas
- ✏️ Editar información de mascotas
- ❌ Eliminar mascotas con confirmación
- 🔄 Actualización automática de la lista
- 📡 Conexión en tiempo real con la API

---

## 🛠️ 💻 Tecnologías utilizadas

- 🧠 Android Studio
- ☕ Java
- 🌐 Volley (HTTP Requests)
- 🔗 API REST
- 📦 JSON
- 🧪 Postman
- 🗂️ Git & GitHub

---

## 🌍 🔗 API utilizada

La aplicación se conecta a la siguiente URL base:

```bash
http://192.168.101.17:3000/mascotas/
```
Puedes cambiarlo dependiendo de tu url local .

---

## 🧠 💡 Funcionamiento del sistema

La aplicación interactúa con una API REST utilizando distintos métodos HTTP:

🔹 GET → Obtiene la lista de mascotas

🔹 POST → Registra una nueva mascota

🔹 PUT → Actualiza los datos de una mascota

🔹 DELETE → Elimina una mascota

## 🚀 Instalación y ejecución
🔹 Clonar el repositorio:
```bash
git clone https://github.com/sandropr24/AppMascotas.git
```
🔹 Abrir el proyecto en Android Studio

🔹 Esperar la sincronización de Gradle

🔹 Ejecutar la aplicación en un emulador o dispositivo físico.

## 📌 Consideraciones

🔌 La API debe estar en ejecución

📶 El dispositivo y el servidor deben estar en la misma red

🧠 Verificar la IP configurada en el proyecto

⚠️ Si no carga datos, revisar conexión con la API

## 👨‍💻 ✨ Autor

Sandro Pachas Romani

📈 Conclusión

>Este proyecto permitió aplicar conocimientos de desarrollo móvil en Android y consumo de APIs REST, logrando implementar correctamente las operaciones CRUD.