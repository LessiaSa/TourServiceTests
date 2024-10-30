# Инструкция по запуску приложения и тестов
## Перед каждым тестом:
1. Открываем страницу http://localhost:8080 в браузере
2. Открываем приложение Docker Desctop на компьютере
3. Открываем приложение для работы с СУБД на компьютере (например DBeaver)
4. Запускаем Docker  в приложении IntelliJIDEA с помощью команды docker compose up
5. Запускаем Jar -файл с помощью команды  java -jar ./artifacts/aqa-shop.jar -P:jdbc.url=jdbc:mysql://localhost:8080/db
