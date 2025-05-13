
/**
 *
 * @returns {HTMLElement}
 */
HTMLElement.prototype.show = function(){
    this.classList.add('-visible');
    return this;
}