import { EmojiButton } from '@joeattardi/emoji-button';
import {inputArea} from './chat-input';

const picker = new EmojiButton({
    showCategoryButtons: false,
    showSearch: false,
    showPreview: false,
    showRecents: false,
    i18n: {
        categories: {
            smileys: 'smileys',
            people: 'people',
            animals: 'nature',
            food: 'food',
            activities: 'activity',
            travel: 'travel',
            objects: 'objects',
            symbols: 'symbols',
            flags: 'flags',
            custom: 'custom'
        }
    },
    styleProperties: {
        '--background-color': '#424242',
        '--font': "'Roboto Mono', monospace",
        '--font-size': '1em',
        '--hover-color': '#42424200',
        '--text-color': '#FFFFFF',
        '--secondary-text-color': '#FFFFFF'
    }
});


const trigger = document.querySelector('#emojiPicker-button');

picker.on('emoji', selection => {
    inputArea.querySelector('textarea').value += selection.emoji;
    setTimeout(() => {
        inputArea.querySelector('textarea').focus();
    }, 0)
});

picker.on('hidden', () => {
    setTimeout(() => {
        inputArea.querySelector('textarea').focus();
    }, 0)
});

trigger.addEventListener('click', () => picker.togglePicker(trigger));