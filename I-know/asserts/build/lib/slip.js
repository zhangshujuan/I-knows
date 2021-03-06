(function(t, e) {
    var i, n, s, r, o, h, u, a, f, c, l, d, p, g, y, v, x, m, S, T, E, b, w, C, _;
    g = void 0;
    f = null;
    m = "x";
    T = "y";
    S = "xy";
    o = "left";
    l = "right";
    y = "up";
    n = "down";
    u = 10;
    h = 40;
    i = ["webkit", "moz", "ms", "o", ""];
    c = /\-?[0-9]+\.?[0-9]*/g;
    r = "ontouchend" in t;
    d = r ? "touchstart": "mousedown";
    a = r ? "touchmove": "mousemove";
    s = r ? "touchend": "mouseup";
    v = t["innerHeight"];
    x = t["innerWidth"];
    b = function() {};
    w = function(t, e) {
        var n, s, r, o, h;
        h = [];
        for (r = 0, o = i.length; r < o; r++) {
            s = i[r];
            n = s ? "" + s + "Transition": "transition";
            h.push(t.style[n] = e)
        }
        return h
    };
    C = function(t, e, n, s) {
        var r, o, h, u, a;
        a = [];
        for (h = 0, u = i.length; h < u; h++) {
            o = i[h];
            r = o ? "" + o + "Transform": "transform";
            a.push(t.style[r] = "translate3d(" + (e || 0) + "px, " + (n || 0) + "px, " + (s || 0) + "px)")
        }
        return a
    };
    E = function(t) {
        var e, n, s, r, o, h, u;
        o = [];
        n = "";
        e = "";
        for (h = 0, u = i.length; h < u; h++) {
            r = i[h];
            s = r ? "" + r + "Transform": "transform";
            n = t.style[s];
            if (n && typeof n === "string") {
                e = n.match(/\((.*)\)/g)[0];
                o = e && e.match(c);
                break
            }
        }
        if (o.length) {
            return {
                x: o[0] || 0,
                y: o[1] || 0,
                z: o[2] || 0
            }
        }
    };
    p = function() {
        var t, e;
        e = [function(t) {
            var e, i;
            i = t.touches && (t.touches.length ? t.touches: [t]);
            e = t.changedTouches && t.changedTouches[0] || t.originalEvent && t.originalEvent.changedTouches && t.originalEvent.changedTouches[0] || i[0].originalEvent || i[0];
            return {
                x: e.clientX,
                y: e.clientY
            }
        },
        function(t) {
            var e;
            e = t;
            return {
                x: e.clientX,
                y: e.clientY
            }
        }];
        t = r ? e[0] : e[1];
        function i(t, e) {
            this.ele = t;
            this.direction = e;
            this._isPressed = false;
            this.onStart = this.onMove = this.onEnd = b;
            this.coord = this.eventCoords = this.cacheCoords = this.finger = this.absFinger = f;
            this.orient = [];
            this.isSlider = false;
            this.isWebapp = false;
            this.duration = "400"
        }
        i.prototype.start = function(t) {
            return (this.onStart = t) && this
        };
        i.prototype.move = function(t) {
            return (this.onMove = t) && this
        };
        i.prototype.end = function(t) {
            return (this.onEnd = t) && this
        };
        i.prototype.setCoord = function(t) {
            var e, i, n;
            i = this.coord = {
                x: t[m] || 0,
                y: t[T] || 0
            };
            n = this.ele;
            C(n, i[m], i[T]);
            for (e in i) {
                n.setAttribute(e, i[e])
            }
            return this
        };
        i.prototype.onTouchStart = function(e) {
            var i;
            this._isPressed = true;
            this.eventCoords = t(e);
            this.cacheCoords = this.coord;
            this.finger = this.absFinger = f;
            if (this.isSlider) {
                this.onSliderStart(e)
            }
            return i = this.onStart.apply(this, [e])
        };
        i.prototype.onTouchMove = function(e) {
            var i, s, r, a, f, c, d, p, g, v, x;
            e.preventDefault();
            if (!this._isPressed) {
                return false
            }
            d = t(e);
            r = this.direction;
            c = this.finger = {
                x: d.x - this.eventCoords.x,
                y: d.y - this.eventCoords.y
            };
            i = this.absFinger = {
                x: Math.abs(c.x),
                y: Math.abs(c.y)
            };
            if (r !== S) {
                p = r === m ? T: m;
                if (i[r] < u || i[p] > h) {
                    return false
                }
            }
            g = [];
            if (i.x > u) {
                g.push(c.x < 0 ? o: l)
            }
            if (i.y > u) {
                g.push(c.y < 0 ? y: n)
            }
            this.orient = g;
            v = this.onMove.apply(this, [e]);
            if (v === false) {
                return false
            }
            a = this.ele;
            f = this.coord = {
                x: r.indexOf(m) < 0 ? this.cacheCoords[m] : this.cacheCoords[m] - 0 + c.x,
                y: r.indexOf(T) < 0 ? this.cacheCoords[T] : this.cacheCoords[T] - 0 + c.y
            };
            C(a, f[m], f[T]);
            x = [];
            for (s in f) {
                x.push(a.setAttribute(s, f[s]))
            }
            return x
        };
        i.prototype.onTouchEnd = function(t) {
            var e, i, n;
            this._isPressed = false;
            e = this.ele;
            if (this.isSlider) {
                this.onSliderEnd(t)
            }
            i = this.onEnd.apply(this, [t]);
            n = E(this.ele);
            if (n) {
                this.setCoord(n)
            }
            return this.orient = []
        };
        i.prototype.onSliderStart = function(t) {
            return w(this.ele, f)
        };
        i.prototype.onSliderEnd = function(t) {
            var e, i, s, r, h, u, a, f, c, d, p, g, v;
            d = this.orient.join("");
            v = 0;
            u = false;
            p = this.page;
            g = this.pageNum;
            s = this.ele;
            i = this.duration;
            e = this.absFinger;
            f = d.indexOf(y) > -1;
            r = d.indexOf(n) > -1;
            h = d.indexOf(o) > -1;
            a = d.indexOf(l) > -1;
            c = this.direction === T;
            if (c) {
                if (f) {
                    p++
                }
                if (r) {
                    p--
                }
            } else {
                if (h) {
                    p++
                }
                if (a) {
                    p--
                }
            }
            if (p === g) {
                p = g - 1;
                u = true
            }
            if (p === -1) {
                p = 0;
                u = true
            }
            if (u === true) {
                i *= c ? e[T] / this.pageHeight: e[m] / this.pageWidth
            }
            w(s, "all " + i + "ms ease-in");
            if (c) {
                v = "-" + p * this.pageHeight;
                C(s, 0, v, 0)
            } else {
                v = "-" + p * this.pageWidth;
                C(s, v, 0, 0)
            }
            return this.page = p
        };
        i.prototype.init = function() {
            var t, e, i, n, r, o, h;
            this.coord = {
                x: 0,
                y: 0
            };
            h = this._onTouchStart = function(t) {
                return function(e) {
                    return t.onTouchStart(e)
                }
            } (this);
            o = this._onTouchMove = function(t) {
                return function(e) {
                    return t.onTouchMove(e)
                }
            } (this);
            r = this._onTouchEnd = function(t) {
                return function(e) {
                    return t.onTouchEnd(e)
                }
            } (this);
            i = this.ele;
            i.addEventListener(d, h, false);
            i.addEventListener(a, o, false);
            i.addEventListener(s, r, false);
            n = this.coord = {
                x: 0,
                y: 0
            };
            e = this.direction;
            C(i, n[m], n[T]);
            for (t in n) {
                i.setAttribute(t, n[t])
            }
            return this
        };
        i.prototype.destroy = function() {
            var t;
            t = this.ele;
            t.removeEventListener(d, this._onTouchStart, false);
            t.removeEventListener(a, this._onTouchMove, false);
            t.removeEventListener(s, this._onTouchEnd, false);
            return this
        };
        i.prototype.slider = function(t) {
            var e, i, n, s, r, h, u, a, f, c;
            r = this.ele;
            if (typeof t === "string") {
                t = r.querySelectorAll(t)
            } else if (!t) {
                t = [];
                i = r.childNodes;
                for (u = 0, f = i.length; u < f; u++) {
                    e = i[u];
                    if (e.nodeType === 1) {
                        t.push(e)
                    }
                }
            }
            this.isSlider = true;
            this.page = 0;
            this.elPages = t;
            s = t.length;
            h = this.pageNum = s ? s: 0;
            if (this.direction === m) {
                for (a = 0, c = t.length; a < c; a++) {
                    n = t[a];
                    n.style.cssFloat = o
                }
            }
            return this
        };
        i.prototype.webapp = function(t) {
            var e, i;
            this.isWebapp = true;
            this.slider(t).fullscreen();
            t = this.elPages;
            e = this.ele;
            i = this.pageNum;
            e.style.height = "" + v * i + "px";
            this.height(v);
            if (this.direction === m) {
                this.width(x)
            }
            return this
        };
        i.prototype.height = function(t) {
            var e, i, n, s, r, o;
            n = this.ele;
            i = this.elPages;
            s = this.pageNum;
            t = String(t).replace("px", "");
            if (t === "100%") {
                t = v
            }
            this.pageHeight = t;
            if (this.direction === m) {
                n.style.height = "" + t + "px"
            }
            for (r = 0, o = i.length; r < o; r++) {
                e = i[r];
                e.style.height = "" + t + "px"
            }
            return this
        };
        i.prototype.width = function(t) {
            var e, i, n, s, r, o;
            n = this.ele;
            i = this.elPages;
            s = this.pageNum;
            t = String(t).replace("px", "");
            if (t === "100%") {
                t = x
            }
            this.pageWidth = t;
            if (this.direction === m) {
                n.style.width = "" + t * s + "px"
            }
            for (r = 0, o = i.length; r < o; r++) {
                e = i[r];
                e.style.width = "" + t + "px"
            }
            return this
        };
        i.prototype.fullscreen = function() {
            var t, e, i;
            e = this.ele;
            t = e;
            while (i = t.parentNode) {
                if (i.nodeType === 1) {
                    i.style.height = "100%";
                    i.style.overflow = "hidden"
                }
                t = i
            }
            return this
        };
        i.prototype.time = function(t) {
            this.duration = String(t).replace("ms", "");
            return this
        };
        return i
    } ();
    _ = function(t, e) {
        var i;
        i = new p(t, e || m);
        return i.init()
    };
    return t.Slip = _
})(window, document);