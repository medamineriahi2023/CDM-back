// Some useful functions
Array.prototype.contains = function (el) {
    return this.indexOf(el) !== -1;
}

Array.prototype.remove = function (el) {
    const index = this.indexOf(el);

    if (index !== -1) {
        return this.splice(index, 1);
    } else {
        return [];
    }
}

Array.prototype.unique = function () {
    return this.filter((el, index) => {
        return this.indexOf(el) === index;
    })
}
