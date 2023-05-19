# Localization

To add another locale, you've to:

1. Create a new json file with the name of the language iso-code (for english this would be ``en.json``)
2. Import the new file in ``src/i18n/index.ts``
3. Add the new imported locale to the messages object in ``src/i18n/index.ts``
4. Add a new option with the iso-code of the newly added locale to the setLocale method signature in
   the ``src/i18n/index.ts``