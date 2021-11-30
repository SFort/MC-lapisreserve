tmp=$(mktemp -d)
forgeryIn="$tmp/forgery-in.jar"
rm build/libs/*-dev.jar build/libs/*-sources.jar
jars=`echo build/libs/*.jar`
unzip -p "$jars" fabric.mod.json > forgery/shadow/fabric.mod.json
cp "$jars" 'forgery/shadow/in.jar'
pushd forgery/shadow
./gradlew clean shadowHack
zip -9 build/libs/out.jar fabric.mod.json
popd
pushd forgery
cp shadow/build/libs/out.jar "$forgeryIn"
./gradlew clean build
popd
java -jar ~/ForgeryTools.jar "$forgeryIn" out.jar ~/.gradle/caches/fabric-loom/mappings/intermediary-1.17.1-v2.tiny ~/.gradle/caches/forge_gradle/minecraft_repo/versions/1.17.1/mcp_mappings.tsrg ./forgery/build/libs/lapisreserve.jar ~/.gradle/caches/fabric-loom/minecraft-1.17.1-intermediary-net.fabricmc.yarn-1.17.1+build.5-v2.jar tf.ssf.sfort.lapisreserve
rm -rf $tmp
