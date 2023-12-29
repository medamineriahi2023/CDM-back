const path = require("path");
const fs = require("fs");

function Serializer() {
    this.outputFolder = path.join(__dirname, "../src/main/resources");
}

Serializer.prototype = {
    constructor: Serializer,

    serialize: function (fileObjects) {
        for (const bundlename in fileObjects) {
            const fileObject = fileObjects[bundlename];

            for (const target in fileObject) {
                const translations = fileObject[target];
                const lines = [];

                for (const key in translations) {
                    const value = translations[key];

                    if (/\n/.test(value)) {
                        // console.log(`[Serializer] ${key}=${value}`)
                    }
                    lines.push(`${key}=${value}`);
                }

                const content = lines.join("\n");

                this.output(bundlename, target, translations);
            }
        }
    },

    output: function (bundlename, language, translations) {
        let filename;
        if (language === "default") {
            filename =
                bundlename === "messages"
                    ? `messages.properties`
                    : `${bundlename}.messages.properties`;
        } else {
            filename =
                bundlename === "messages"
                    ? `messages_${language}.properties`
                    : `${bundlename}.messages_${language}.properties`;
        }

        const filepath = path.join(this.outputFolder, filename);

        fs.readFile(filepath, "utf8", function (err, data) {
            if (err) {
                console.error(err);
                return;
            }

            for (const key in translations) {
                const regex = new RegExp(key + "=.+", "g");
                const value = translations[key];

                // If key is already in file, we perform a replace
                if (regex.test(data)) {
                    const replaceRegex = new RegExp(
                        key + "=(.*[\\\\]\\s*)*.*",
                        "g"
                    );

                    data = data.replace(replaceRegex, `${key}=${value}`);
                } else {
                    data += `${key}=${value}\n`;
                }
            }

            fs.writeFile(filepath, data, "utf-8", function (err) {
                if (err) {
                    console.error(err);
                } else {
                    console.log("[INFO] Writing " + filename + " complete");
                }
            });
        });
    },
};

exports = module.exports = Serializer;
