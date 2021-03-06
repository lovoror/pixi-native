package pixi.math

import org.khronos.webgl.Float32Array
import pixi.utils.set

data class Matrix(var a: Float = 1.0f, var b: Float = 0.0f,
                  var c: Float = 0.0f, var d: Float = 1.0f,
                  var tx: Float = 0.0f, var ty: Float = 0.0f) {
    var array: Float32Array? = null

    fun identity() {
        a = 1.0f
        b = 0.0f
        c = 0.0f
        d = 1.0f
        tx = 0.0f
        ty = 0.0f
    }

    fun toArray(transpose: Boolean, out: Float32Array? = null): Float32Array {
        val array: Float32Array = out ?: this.array ?: Float32Array(9)
        if (this.array == null) {
            this.array = array
        }

        if (transpose) {
            array[0] = this.a;
            array[1] = this.b;
            array[2] = 0f;
            array[3] = this.c;
            array[4] = this.d;
            array[5] = 0f;
            array[6] = this.tx;
            array[7] = this.ty;
            array[8] = 1f;
        } else {
            array[0] = this.a;
            array[1] = this.c;
            array[2] = this.tx;
            array[3] = this.b;
            array[4] = this.d;
            array[5] = this.ty;
            array[6] = 0f;
            array[7] = 0f;
            array[8] = 1f;
        }
        return array
    }

    fun append(matrix: Matrix): Matrix {
        val a1 = this.a
        val b1 = this.b
        val c1 = this.c
        val d1 = this.d

        this.a = matrix.a * a1 + matrix.b * c1
        this.b = matrix.a * b1 + matrix.b * d1
        this.c = matrix.c * a1 + matrix.d * c1
        this.d = matrix.c * b1 + matrix.d * d1

        this.tx = matrix.tx * a1 + matrix.ty * c1 + this.tx
        this.ty = matrix.tx * b1 + matrix.ty * d1 + this.ty

        return this
    }
}
