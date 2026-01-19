## TODO
### Usunąć LocalStorage
- teraz zapisuje token do localStorage, ale to jest podatne na XSS. Dla wersji produkcyjnej będzie trzeba to zmienić na przykład na HttpOnly cookies z refresh tokenem i endpointed odświeżania.
### Obsługa wygaśnięcia tokena
- wygaśnięcie tokena nie jest na razie obsługiwane
### Dodać ogólny plik `src/api/path`
- plik w którym są wszystkie ścieżki api z backendu
- taka przebudowa pozwoli oszczędzić błędów (literówek)
- kod czytelniejszy