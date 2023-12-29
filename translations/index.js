require('./array');

const { getMessageFiles, bundleMessageFiles } = require('./properties');
const { Translator } = require('./translator');
const Serializer = require('./serializer');

const {
    listUntranslatedKeys,
    listLabelsMissingFromDefault,
    listLabelsMissingFromTranslations
} = require('./checkers');

/**
 * Raise errors for keys that are found in translation files but not in default files
 * @param { { [bundlename: string]: string[] } } missingKeysByBundle List of keys that are missing, ordered by bundle names
 */
function alertMissingKeysFromDefault(missingKeysByBundle) {
    for (const bundlename in missingKeysByBundle) {
        const missingKeys = missingKeysByBundle[bundlename];

        console.error('[ERROR] In bundle ' + bundlename + '. The following keys were found in at least one translation file but are missing from default file:');
        missingKeys.forEach((missingKey) => {
            console.error('\t-"' + missingKey + '"');
        })
    }

    if (Object.keys(missingKeysByBundle).length) {
        console.log('\n');
        throw new Error('Application has crashed since some translations aren\'t properly configured. There should be more information about this in the output above');
    }
}

/**
 * This function will convert into a list of labels the result of listLabelsMissingFromDefault
 *
 * @param { {[language: string]: string[]} } fileObject A fileObject as returned by listLabelsMissingFromDefault
 */
function checkLabelsMissingFromBundleDefault(fileObject) {
    const foundMissingKeys = [];

    for (const language in fileObject) {
        const missingKeys = fileObject[language];

        foundMissingKeys.push(...missingKeys);
    }

    return foundMissingKeys.unique();
}

function checkLabelsMissingFromDefaults(fileObjects) {
    const missingKeysInDefaults = {};

    for (const bundlename in fileObjects) {
        const fileObject = fileObjects[bundlename];
        const missingKeysInDefault = listLabelsMissingFromDefault(fileObject);

        const foundMissingKeysInBundleDefault = checkLabelsMissingFromBundleDefault(missingKeysInDefault);

        if (foundMissingKeysInBundleDefault.length) {
            missingKeysInDefaults[bundlename] = foundMissingKeysInBundleDefault;
        }
    }

    alertMissingKeysFromDefault(missingKeysInDefaults);
}

/**
 * Log every keys for which no entry was found in files. These are not errors so it won't cause the app to crash since it can handle this case
 *
 * @param { { [bundlename: string]: string[] } } missingKeysByBundle List of keys that are missing in translation files, ordered by bundle names
 */
function alertMissingKeysFromTranslations(missingKeysInBundlesTranslations) {
    for (const bundlename in missingKeysInBundlesTranslations) {
        const missingKeysInBundleTranslations = missingKeysInBundlesTranslations[bundlename];

        console.warn('[WARN] In bundle ' + bundlename + '. The following keys were found in default file but are missing from at least one translation file:');

        for (const language in missingKeysInBundleTranslations) {
            const missingKeysInBundleTranslation = missingKeysInBundleTranslations[language];

            console.warn(`[WARN] Language: ${language}`)
            console.warn('\t- ' + missingKeysInBundleTranslation.join('\n\t- '));
        }
    }

    if (Object.keys(missingKeysInBundlesTranslations).length) {
        console.warn('\n[WARN] All the previously mentionned missing keys were automatically added to files and will be automatically translated during next steps');
    }
}

function checkLabelsMissingFromBundlesTranslations(fileObjects) {
    const missingKeysInBundlesTranslations = {};

    for (const bundlename in fileObjects) {
        const fileObject = fileObjects[bundlename];
        const missingKeysInBundleTranslations = listLabelsMissingFromTranslations(fileObject);

        for (const language in missingKeysInBundleTranslations) {
            const missingKeysInBundleTranslation = missingKeysInBundleTranslations[language];

            if (missingKeysInBundleTranslation.length) {
                if (!missingKeysInBundlesTranslations[bundlename]) {
                    missingKeysInBundlesTranslations[bundlename] = {};
                }

                missingKeysInBundlesTranslations[bundlename][language] = [...missingKeysInBundleTranslation]

                missingKeysInBundleTranslation.forEach(missingKeyInBundleTranslation => {
                    const defaultValue = fileObject.default[missingKeyInBundleTranslation];

                    fileObject[language][missingKeyInBundleTranslation] = defaultValue;
                })
            }
        }
    }

    alertMissingKeysFromTranslations(missingKeysInBundlesTranslations);
}

async function main() {
    const messageFiles = await getMessageFiles();
    const fileObjects = await bundleMessageFiles(messageFiles);

    const translator = new Translator();
    const serializer = new Serializer();

    // Checking keys missing from defaults in every bundles. Any result here will cause the script to crash
    checkLabelsMissingFromDefaults(fileObjects);

    // After this call, fileObjects will be changed so it contains every missing keys in bundles
    checkLabelsMissingFromBundlesTranslations(fileObjects);

    // Now translating everything
    await translator.translateFileObjects(fileObjects);

    serializer.serialize(fileObjects);

}

main();
