const { splitTranslationsFromDefaults } = require('./properties');

/**
 * Lists all unstranslated keys in a bundle.
 * That means that keys which are in default but not in others won't be returned by this.
 * Same goes for keys that are present in language file but not in default.
 *
 * @param {TranslationsBundle} fileObject Result of a call to bundleMessageFiles()
 *
 */
const listUntranslatedKeys = (fileObject) => {
    const { defaults, translations } = splitTranslationsFromDefaults(fileObject);

    const untranslatedLabels = {};

    for (const key in defaults) {
        const defaultValue = defaults[key];

        for (const translationKey in translations) {
            if (!untranslatedLabels[translationKey]) {
                untranslatedLabels[translationKey] = [];
            }

            const translation = translations[translationKey];
            const translatedValue = translation[key];
            if (translatedValue === defaultValue) {
                untranslatedLabels[translationKey].push(key);
            }
        }
    }

    return untranslatedLabels;
}

const listUntranslatedKeyValues = (fileObject) => {
    const result = {
        fr: { /* {[key: string]: string } */ },
        en: { /* {[key: string]: string } */ },
        es: { /* {[key: string]: string } */ },
        nl: { },
        ar: { }
    };

    const untranslatedLanguages = listUntranslatedKeys(fileObject);

    for (const language in untranslatedLanguages) {
        const keys = untranslatedLanguages[language];

        for (const key of keys) {
            const untranslatedValue = fileObject[language][key];

            result[language][key] = untranslatedValue;
        }
    }

    return result;
}

/**
 * Lists all values from A that are not present in B
 *
 * @param {string[]} a
 * @param {string[]} b
 *
 * @returns {string[]} List of all values that were found in A but are not present in B
 */
const listMissingValues = (arrayA, arrayB) => {
    const results = [];

    for (const a of arrayA) {
        if (!arrayB.contains(a)) {
            results.push(a);
        }
    }

    return results;
}

/**
 * Lists all keys from A that are not present in B
 *
 * @param {string[]} a
 * @param {string[]} b
 *
 * @returns {string[]} List of all keys that were found in A but are not present in B
 */
const listMissingKeys = (a, b) => {
    return listMissingValues(Object.keys(a), Object.keys(b));
}

const listLabelsMissingFromDefault = (fileObject) => {
    const { defaults, translations } = splitTranslationsFromDefaults(fileObject);

    const results = {};

    for (const language in translations) {
        const translation = translations[language];

        results[language] = listMissingKeys(translation, defaults); // List keys present in translation but not in defaults
    }

    return results;
}

const listLabelsMissingFromTranslations = (fileObject) => {
    const { defaults, translations } = splitTranslationsFromDefaults(fileObject);
    const results = {};

    for (const language in translations) {
        const translation = translations[language];

        results[language] = listMissingKeys(defaults, translation);
    }

    return results;
}

exports = module.exports = {
    listUntranslatedKeys,
    listUntranslatedKeyValues,
    listLabelsMissingFromDefault,
    listLabelsMissingFromTranslations
}
