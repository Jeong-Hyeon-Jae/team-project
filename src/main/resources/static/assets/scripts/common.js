/**
 *
 * @return {HTMLElement}
 */
HTMLElement.prototype.show=function(){
    this.classList.add('-visible');
    return this;
}
/**
 * @return {HTMLElement}
 */
HTMLElement.prototype.hide=function(){
    this.classList.remove('-visible');
    return this;
}


class Dialog {
    /**
     * @private
     * @type {HTMLElement}
     */
    #$element;
    /**
     * @private
     * @type {HTMLElement}
     */
    #$modal = [];

    constructor() {
        this.#$element = document.body.querySelector(".--dialog");
        if (this.#$element == null) {
            this.#$element = document.createElement('div');
            this.#$element.classList.add('--dialog');
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
     * @param{{
     *     title:string,
     *     content:string,
     *     buttons?:{
     *       caption:string,
     *       color?:'purple'|'gray'|'red',
     *       onclick?:function(HTMLElement)
     *     }[],
     *     isContentHtml?:boolean,
     *     delay?:number
     * }} args
     * @return {HTMLElement}
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
     * @param {{
     * buttonCaption?:string,
     *      isContentHtml?:boolean,
     *      delay?:number,
     *      buttonColor?:'purple'|'red'|'gray',
     *      onOkCallback?:function(HTMLElement?)
     * }?}args
     * @return {HTMLElement}
     */
    showSimpleOk(title, content, args) {
        args ??= {};
        return this.show({
                title:title,
                content:content,
                buttons:[
                    {
                        caption:args.buttonCaption??'확인',
                        color: args.buttonColor ?? 'purple',
                        onclick:($modal)=>{
                            if(typeof args.onOkCallback==='function'){
                                args.onOkCallback($modal);
                            }
                            this.hide($modal);
                        }
                    },

                ],
            isContentHtml: args.isContentHtml,
            delay: args.delay
            }
        )
    }
}

window.addEventListener('DOMContentLoaded', () => {
    //                            ⬆defer와 같다.
    window.dialog = new Dialog();
    console.log(dialog)
})