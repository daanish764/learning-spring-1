//export const authMethod = 'BASIC_AUTH'
export const authMethod = 'TOKEN';

export function isValid(value: any): boolean {
    return value != null && value !== '' && 
           !(Array.isArray(value) && value.length === 0) && 
           !(typeof value === 'object' && Object.keys(value).length === 0);
};