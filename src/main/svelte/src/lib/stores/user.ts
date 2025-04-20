import { writable } from 'svelte/store';

export const userUUID = writable<string | null>(null);