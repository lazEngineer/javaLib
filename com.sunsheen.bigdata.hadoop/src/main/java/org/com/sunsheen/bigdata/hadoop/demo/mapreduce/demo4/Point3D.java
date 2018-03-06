package org.com.sunsheen.bigdata.hadoop.demo.mapreduce.demo4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Point3D implements WritableComparable {
	public float x;
	public float y;
	public float z;

	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3D() {
		this(0.0f, 0.0f, 0.0f);
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void write(DataOutput out) throws IOException {
		out.writeFloat(x);
		out.writeFloat(y);
		out.writeFloat(z);
	}

	public void readFields(DataInput in) throws IOException {
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
	}

	public String toString() {
		return Float.toString(x) + ", " + Float.toString(y) + ", "
				+ Float.toString(z);
	}

	public float distanceFromOrigin() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public int compareTo(Object other) {
		float myDistance = this.distanceFromOrigin();
		float otherDistance = ((Point3D) other).distanceFromOrigin();

		return Float.compare(myDistance, otherDistance);
	}

	public boolean equals(Object o) {
		Point3D other = (Point3D) o;
		if (!(other instanceof Point3D)) {
			return false;
		}

		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

	public int hashCode() {
		return Float.floatToIntBits(x) ^ Float.floatToIntBits(y)
				^ Float.floatToIntBits(z);
	}

}
