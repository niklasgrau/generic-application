export interface MultiLanguageLabel {
    labels: {[key:string]: Map<string, string>}
}

export function isMultiLanguageLabel(obj: any): obj is MultiLanguageLabel {
    return 'labels' in obj;
}

// export class MultiLanguageLabel {
//     private readonly _labels: Map<String, String>;
//
//     constructor(mlLabel: string) {
//         this._labels = new Map<String, String>();
//
//         const regex = new RegExp('\\[(.{2}):([^\\]]*)\\]', 'g')
//         let matches: RegExpMatchArray | null;
//         while ((matches = regex.exec(mlLabel)) !== null) {
//             // This is necessary to avoid infinite loops with zero-width matches
//             if (matches.index === regex.lastIndex) {
//                 regex.lastIndex++;
//             }
//
//             this._labels.set(matches[1], matches[2])
//         }
//     }
//
//     get labels(): Map<String, String> {
//         return this._labels;
//     }
//
//     public getLabel(lang: string, required: boolean = false) {
//         return required ? `${this._labels.get(lang)}*` : this._labels.get(lang);
//     }
//
//     public isEmpty(): boolean {
//         return this._labels.size === 0;
//     }
// }

export function getLabel(lang: string, required: boolean = false, mlLabel: string) {
    let labels = new Map<String, String>();
    const regex = new RegExp('\\[(.{2}):([^\\]]*)\\]', 'g')
    let matches: RegExpMatchArray | null;
    while ((matches = regex.exec(mlLabel)) !== null) {
        // This is necessary to avoid infinite loops with zero-width matches
        if (matches.index === regex.lastIndex) {
            regex.lastIndex++;
        }

        labels.set(matches[1], matches[2])
    }

    return required ? `${labels.get(lang)}*` : labels.get(lang);
}