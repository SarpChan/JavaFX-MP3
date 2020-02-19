MP3 Player auf JavaFX Basis mit Spotify Anbindung als Projekt im 3. Semester.

Leider wurde das Projekt gestartet und beendet, bevor wir Wissen zu gradle/maven hatten. Aufgrund des geringen 
persönlichen Nutzens, wird das auch nicht mehr upgedated. 

Es wird auf die public api von Spotify zugegriffen, um Daten zu Liedern, die sich als MP3 auf dem NutzerPC finden. 
Daten, die ausgelesen werden, sind Beats pro Minute (BPM), Energie, Valenz (Positivität), Tanzbarkeit, Lebendigkeit, Akustizität 
und Instrumentalität eines Liedes. 

Dafür versucht das Programm anhand vom Liednamen und Künstler das Spotify Pendant zu finden. Ist dies erfolgreich,
wird eine Lied ID zurückgeschickt, mit der dann die obenstehenden Daten ausgelesen werden können. 

Es können Playlists automatisch anhand von Eingränzungen dieser Werte erstellt werden.  

Beim Starten des Programms wird rekursiv im Musik Ordner des Nutzers nach Liedern (.mp3) und Playlisten (.m3u) gesucht. 
Diese Lieder und Playlisten werden auf Dopplungen überprüft und dargestellt. 

Das Programm erstellt zudem anhand mit allen gefundenen Liedern und Playlisten (m3u's halten pro Zeile nur einen
Pfad zu einer .mp3 Datei), die auf gültige mp3s verweisen, neue Playlisten, die zu einem Mood passen. Es werden 
Workout und Chillout Playlisten erstellt. 
