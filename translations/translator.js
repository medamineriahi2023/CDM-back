const { Translate } = require("@google-cloud/translate").v2;
const { listUntranslatedKeyValues } = require("./checkers");

function Translator() {
    this.apiKey = "AIzaSyBBhXBU-qEFLe3gHei7A0bgiI0rg44Yonc";

    this.googleTranslator = new Translate({
        projectId: "oga-translate-script",
        key: this.apiKey,
    });
}

Translator.prototype = {
    constructor: Translator,

    translateFileObject: async function (fileObject) {
        const untranslatedLanguages = listUntranslatedKeyValues(fileObject);

        const translations = { ...fileObject };

        for (const target in untranslatedLanguages) {
            const untranslatedLanguage = untranslatedLanguages[target];

            const labels = Object.keys(untranslatedLanguage);
            for (const label of labels) {
                const value = untranslatedLanguage[label];

                // Translate from french to wanted language
                const from = "fr";
                let [translation] =
                    from !== target
                        ? await this.googleTranslator.translate(value, {
                              from,
                              to: target,
                          })
                        : [value];

                console.log(label + " (" + target + ") => " + translation);

                translations[target][label] = translation;

                if (value === translation) {
                    translations["default"][label] = value + "_";
                }
            }
        }

        return translations;
    },

    translateFileObjects: async function (fileObjects) {
        const result = {};

        for (const bundlename in fileObjects) {
            const fileObject = fileObjects[bundlename];
            const translatedFileObject = await this.translateFileObject(
                fileObject
            );

            result[bundlename] = translatedFileObject;

            // console.info('[INFO] Completed translation of', bundlename);
        }

        return result;
    },
};

exports = module.exports = {
    Translator,
};
