# Pt3 - Gestió de Videojocs (Serialització d'objectes)

Aplicació Java de consola per gestionar un catàleg de videojocs (CRUD complet) amb persistència en el fitxer binari `videojocs.dat`, usant `ObjectOutputStream` i `ObjectInputStream`.

## Fitxers
- `Videojoc.java`: classe `Serializable` (títol, gènere, any de llançament, plataforma, preu).
- `GestioVideojocs.java`: menú, CRUD i càrrega/desat del fitxer.

## Com executar-ho
```
javac Videojoc.java GestioVideojocs.java
java GestioVideojocs
```

## Menú
1. Afegir videojoc
2. Llistar tots els videojocs
3. Cercar videojocs per títol (coincidència parcial)
4. Actualitzar un videojoc
5. Eliminar un videojoc
6. Sortir (desa els canvis)

## Problemàtiques de la serialització i dels fitxers binaris

1. **Dependència de la versió de la classe.** Si es modifica `Videojoc` (afegir/treure atributs) i no es controla `serialVersionUID`, la lectura falla amb `InvalidClassException`. Fixar-lo ajuda, però canvis incompatibles poden donar dades incorrectes o perdudes.
2. **No interoperable.** El format és propi de Java: un `.dat` no es pot llegir des de Python, JavaScript, etc. Formats com JSON, XML o CSV sí que són portables.
3. **Inseguretat.** Deserialitzar dades no fiables és una vulnerabilitat greu (deserialization attacks): un fitxer manipulat pot executar codi o provocar denegació de servei durant `readObject()`.
4. **No és llegible ni editable.** No es pot obrir amb un editor de text ni revisar o corregir a mà, i dificulta la depuració.
5. **Fragilitat davant corrupció.** Si el fitxer es talla o es corromp (tall de llum, error d'escriptura), tot el catàleg pot quedar il·legible, ja que es desa com un únic objecte.
6. **Reescriptura completa.** Cada canvi implica tornar a escriure tot el fitxer; amb moltes dades és ineficient i no permet accedir a un sol registre.
7. **Cast no comprovat.** `readObject()` retorna `Object`, cal un cast a `ArrayList<Videojoc>` que el compilador no pot verificar (`@SuppressWarnings("unchecked")`).
8. **Atributs i referències.** Tots els atributs han de ser serialitzables o marcar-se `transient`; la deserialització no invoca el constructor, de manera que es poden saltar invariants de la classe.
9. **Sense concurrència.** No hi ha control d'accés simultani: dos processos escrivint el mateix fitxer poden corrompre'l.
10. **Detecció de final de fitxer.** `available()` no és fiable; amb diversos objectes cal capturar `EOFException`. Per això aquí es desa la llista sencera.

Alternatives més robustes: JSON (Gson/Jackson), XML, CSV o una base de dades (SQLite, etc.).
