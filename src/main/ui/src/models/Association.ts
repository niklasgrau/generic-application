


export interface Association {
    // Configuration fields
    label: string,
    path: string,
    url: string,
    identifierPath: string,
    singleton: string,
    columns: Array<object>,

    // dynamic fields
    isLoading: boolean,
    items: Array<object>,
    selected: number | null,
    menu: boolean,
    error: boolean,
    errorText: string

}
