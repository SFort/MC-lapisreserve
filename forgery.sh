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
echo "\nNOTICE: Do not let forgery fool you it _IS_ a magic fabric to forge bullet that solves all my problems\n"
tsrg="$tmp/mcp_mappings.tsrg"
tail -n +2 ~/.gradle/caches/forge_gradle/minecraft_repo/versions/1.18.1/mcp_mappings.tsrg | sed -E '/^\s+static/d' | sed -E 's/ [0-9]+$//' > $tsrg
java -jar ~/ForgeryTools.jar "$forgeryIn" out.jar ~/.gradle/caches/fabric-loom/1.18/net.fabricmc.yarn.1_18.1.18+build.1-v2/mappings.tiny $tsrg ./forgery/build/libs/lapisreserve.jar ~/.gradle/caches/fabric-loom/1.18/net.fabricmc.yarn.1_18.1.18+build.1-v2/minecraft-intermediary.jar tf.ssf.sfort.lapisreserve
rm -rf $tmp
