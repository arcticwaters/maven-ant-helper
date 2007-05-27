build: build/maven-ant-helper.jar

build/maven-ant-helper.jar: $(wildcard src/main/java/*.java)
	javac \
		-cp /usr/share/java/ant.jar:/usr/share/java/modello-core.jar \
		-d build/classes \
		src/main/java
	jar cf build/maven-ant-helper.jar -C build/classes .

clean:
	rm -rf build

install:
	install -d -o root -g root $(DESTDIR)/usr/share/maven-ant-helper
	install -o root -g root maven-build.xml $(DESTDIR)/usr/share/maven-ant-helper
	install -o root -g root maven-defaults.properties $(DESTDIR)/usr/share/maven-ant-helper
	install -o root -g root build/maven-ant-helper.jar $(DESTDIR)/usr/share/maven-ant-helper
