build: build/maven-ant-helper.jar

build/maven-ant-helper.jar: $(wildcard src/main/java/*.java)
	/usr/lib/jvm/java-gcj/bin/javac \
		-cp /usr/share/java/ant.jar \
		-d build/classes \
		$(wildcard src/main/java/*.java)
	jar cf build/maven-ant-helper.jar -C build/classes .

clean:
	rm -rf build

install:
	install -d -o root -g root $(DESTDIR)/usr/share/maven-ant-helper
	install -o root -g root -m 644 maven-build.xml $(DESTDIR)/usr/share/maven-ant-helper
	install -o root -g root -m 644 maven-defaults.properties $(DESTDIR)/usr/share/maven-ant-helper
	install -o root -g root -m 644 build/maven-ant-helper.jar $(DESTDIR)/usr/share/maven-ant-helper
