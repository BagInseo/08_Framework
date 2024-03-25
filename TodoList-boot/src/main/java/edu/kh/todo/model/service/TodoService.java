package edu.kh.todo.model.service;

import java.util.Map;

import edu.kh.todo.model.dto.Todo;

public interface TodoService {

	/** 할 일 목록 + 완료된 할 일 조회
	 * @return map
	 */
	Map<String, Object> selectAll();

	/**할 일 추가
	 * @param todoTitle
	 * @param todoContent
	 * @return
	 */
	int addTodo(String todoTitle, String todoContent);

	
	/** 할 일 상세 조회
	 * @param todoNo
	 * @return todo
	 */
	Todo todoDetail(int todoNo);

	/**할 일 삭제
	 * @param todoNo
	 * @return result
	 */
	int todoDelete(int todoNo);

	int todoUpdate(Todo todo);

	int changeComplete(Todo todo);

	/** 전체 할 일 개수 조회
	 * @return totalCount
	 */
	int getTotalCount();

	/** 완료된 할 일 개수 조회
	 * @return completeCount
	 */
	int getCompleteCount();


}
