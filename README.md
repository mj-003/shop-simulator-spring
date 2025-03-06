# Sklep internetowy - Spring Boot

## Opis
Aplikacja sklepu internetowego napisana w **Spring Boot**. Umożliwia logowanie użytkowników i administratorów. Klient może przeglądać produkty, dodawać je do koszyka i składać zamówienia. Administrator może zarządzać produktami i kategoriami.

<img width="1151" alt="Zrzut ekranu 2025-03-6 o 09 50 57" src="https://github.com/user-attachments/assets/e90290b9-8796-4d56-bc28-f49df3487a58" /> 
<img width="1151" alt="Zrzut ekranu 2025-03-6 o 09 50 45" src="https://github.com/user-attachments/assets/6bebfdaf-1a01-46dd-895e-24407a1d7a7d" /> 
<img width="1151" alt="Zrzut ekranu 2025-03-6 o 09 47 26" src="https://github.com/user-attachments/assets/cf51515d-6400-4e4f-8ac1-b928c49ab0bc" />


## Technologie
- **Backend**: Java, Spring Boot, Spring Security, JPA, MySQL
- **Frontend**: Thymeleaf 

## Struktura projektu
- **Model** – definicje encji (Product, CartItem, Order)
- **Repository** – operacje na bazie danych
- **Service** – logika biznesowa (zarządzanie koszykiem, produktami, zamówieniami)
- **Controller** – obsługa żądań HTTP (ProductController, CartController, OrderController)

## Instalacja
1. Skonfiguruj MySQL i utwórz bazę `shop_db`.
2. Skonfiguruj `application.properties`.
3. Uruchom aplikację:
   ```sh
   mvn spring-boot:run
   ```


