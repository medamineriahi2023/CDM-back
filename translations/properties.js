require('./array');

const path = require('path');
const fs = require('fs');

const resourcesFolderPath = path.join(__dirname, '../src/main/resources');

/**
 * Scans the application to find all files *.properties
 * Message files are then separated from config files and are returned via resolve() call
 */
const getMessageFiles = async () =>
    new Promise((resolve, reject) => {
        fs.readdir(resourcesFolderPath, (err, files) => {
            if (err) {
                reject('Unable to scan directory: ', err);
            }

            propertiesFiles = files.filter(filename => {
                const isPropertiesFile = /.*\.properties$/.test(filename);
                const isConfigFile = /^application\..*\.?properties$/.test(filename) || filename === 'build.properties';

                return isPropertiesFile && !isConfigFile;
            })

            resolve(propertiesFiles);
        })
    })

/**
 * This function maps a .properties file (KEY=value) to JS objects { KEY: value }
 *
 * @param {string} filename The name of the file we want to extract properties from
 *
 * Once JS Object is extracted it is returned via resolve() call
 */
const getProperties = async (filename) =>
    new Promise((resolve, reject) => {
        fs.readFile(path.join(resourcesFolderPath, filename), (err, fileContent) => {
            if (err) {
                reject(err);
            }

            fileContent = fileContent.toString();

            const result = {};
            const lines = fileContent.split('\n');

            for (let i = 0; i < lines.length; i++) {
                let line = lines[i];
                if (/^\#/.test(line) || line.indexOf("=") === -1) {
                    continue;
                }

                const lineSplit = line.split('=');
                const key = lineSplit[0];
                lineSplit.splice(0,1);
                let value = lineSplit.join("=");


                while (/\\ *$/.test(line)) {
                    i++;
                    line = lines[i];
                    value += '\n' + line;
                }

                result[key] = value;
            }

            resolve(result);
        })
    })

/**
 * This function takes a list of filenames and groups them by label in a JS Object
 * For instance ["excel.messages.properties", "excel.messages_fr.properties", "excel.messages_en.properties", "excel.messages_es.properties"]
 *
 *
 * @param {string[]} filenames List of filenames to bundle
 *
 * @returns { { [language: string]: { [key: string ]: string }} } keys and values grouped by language. Files with no language indicator will have the label "default"
 */
const bundleMessageFiles = async (filenames) => {
    const labels = [];
    const results = {};

    for (let i = 0; i < filenames.length; i++) {
        const filename = filenames[i];

        let [label, languagePart, extension] = filename.split('.');
        let language = null;

        if (!extension) {
            [label, language] = label.split('_');
        } else {
            [useless, language] = languagePart.split('_');
        }

        if (!language) {
            language = 'default';
        }

        if (!labels.contains(label)) {
            labels.push(label);
            results[label] = {};
        }

        results[label][language] = await getProperties(filename);
    }

    return results;
}

const splitTranslationsFromDefaults = (fileObject) => {
    const defaults = fileObject.default;

    const translations = { ...fileObject };
    delete translations.default;

    return {
        defaults,
        translations
    }
}

exports = module.exports = {
    getMessageFiles,
    getProperties,
    bundleMessageFiles,
    splitTranslationsFromDefaults
}
