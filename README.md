# ДЗ_11

**Задание 1.** Необходимо создать программу, которая продемонстрирует утечку памяти в Java. При этом объекты должны не только создаваться, но и периодически частично удаляться, чтобы GC имел возможность очищать часть памяти. Через некоторое время программа должна завершиться с ошибкой OutOfMemoryError c пометкой Java Heap Space.  

**Задание 2.** Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace /Permanent Generation  

:warning:  

Задание 1 необходимо запускать с параметром **-Xmx64m**  
Задание 2 необходимо запускать с параметрами **-Xmx100m -XX:MaxMetaspaceSize=32m**
