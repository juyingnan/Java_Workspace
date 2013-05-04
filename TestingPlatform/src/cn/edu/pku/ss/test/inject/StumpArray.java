package cn.edu.pku.ss.test.inject;

import java.util.ArrayList;

public class StumpArray{

	ArrayList<Stump> array = new ArrayList<Stump>();

	/**
	 * Add a stump the the list, the attribute id will be set automatically
	 *
	 * @param att a StumpAttribute object
	 * @return int the att Id
	 * */
	public int add(Stump att){
		att.setId(array.size());
		array.add(att);
		return this.size();
	}

    /**
     * Returns the number of elements in this list.
     *
     * @return  the number of elements in this list.
     */
	public int size() {
		return array.size();
	}

	/**
	 * @param  index index of element to return.
     * @return the element at the specified position in this list.
     * @throws    IndexOutOfBoundsException if index is out of range <tt>(index
     * 		  &lt; 0 || index &gt;= size())</tt>.
	 * */
	public Stump get(int index){
		return array.get(index);
	}


	public void clear(){
		array.clear();
	}

	public Stump remove(int index){
		return array.remove(index);
	}

	public boolean remove(Stump att){
		return array.remove(att);
	}
}
