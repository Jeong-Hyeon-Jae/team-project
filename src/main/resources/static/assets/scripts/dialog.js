class Dialog {
    #$element;
    #$modal = [];

    constructor() {
        this.#$element = document.body.querySelector(".-object-dialog");
        if (this.#$element == null) {
            this.#$element = document.createElement('div');
            this.#$element.classList.add('-object-dialog');
            document.body.insertAdjacentElement('afterbegin', this.#$element);

        }
    }

    /**
     *
     * @param {HTMLElement} $modal
     * @returns {boolean}
     */
    hide($modal) {
        $modal.hide();
        const index = this.#$modal.indexOf($modal);
        if (index === -1) {
            return false;
        }
        this.#$modal.splice(index, 1);
        if (this.#$modal.length === 0) {
            this.#$element.hide();
        }
        setTimeout(() => $modal.remove(), 1000);
        return true;
    }

    /**
     *
     * @param {{title:String, content:string,button?:{caption:string,color?:'gray'|'purple'|'red',onclick:function(HTMLElement?)}[], isContentHtml?:boolean|undefined}}args
     */
    show(args) {
        args.isContentHtml ??= false;
        const $modal = document.createElement('div');
        $modal.classList.add('--modal');

        const $title = document.createElement('div');
        $title.classList.add('--title');
        $title.innerText = args.title;
        const $content = document.createElement('div');
        $content.classList.add('--content')
        if (args.isContentHtml === true) {
            $content.innerHTML = args.content;
        } else {
            $content.innerText = args.content;
        }
        $modal.append($title, $content);
        if (args.button != null && args.button.length > 0) {
            const $buttonContainer = document.createElement('div');
            $buttonContainer.classList.add('--button-conatiner');
            for (const button of $buttonContainer) {
                const $button = document.createElement('button');
                $button.classList.add('--button','-object-button',`--button-${button.color ??'gray'}`)
                $button.setAttribute('type', 'button');
                $button.innerText = button.caption;
                $button.addEventListener('click', () => button.onclick($modal));
                $buttonContainer.append($button);
            }
            $modal.append($buttonContainer);
            this.#$element.append($modal);
            this.#$element.show();
            this.#$modal.push($modal);
            setTimeout(() => $modal.show(), 50);
        }

    }
    /**
     *
     * @param {string}title
     * @param {string}content
     * @param {function(HTMLElement?)|undefined} onclick
     * @return {HTMLElement}
     */
    showSimpleOk(title, content, onclick = undefined) {
        return this.show({
            title:title,
            content:content,
            buttons:[
                {
                    caption:'확인',
                    onclick:($modal)=>{
                        if(typeof onclick==='function'){
                            onclick($modal);
                        }
                        this.hide($modal);
                    }
                },

            ]
            }
        )
    }
}