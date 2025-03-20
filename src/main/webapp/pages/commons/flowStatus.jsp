<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="/WEB-INF/common-util.tld" prefix="pi"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<body >

	<table class="auditTable">
		<c:choose>
			<c:when test='${flowType == "newPrd" || flowType == "modifyPrd" || flowType == "ec"}'>
				<tr>
					<td class="title">審核流程：</td>
					<td class="context"><c:out value="${flowTypeNm}"/></td>
					<td class="title">審核狀態：</td>
					<td class="context" >
						<c:choose>
						  	<c:when test='${flowType == "ec"}'>
						    	<pi:formatJsonCode type="flowAuditStatusEc" value="${quotation.flowStatusEc}" />
						  	</c:when>
						  	<c:otherwise>
						    	<pi:formatJsonCode type="flowStatus" value="${quotation.flowStatus}" />
						  	</c:otherwise>
						</c:choose>
					</td>
					<td class="title">提報日期：</td>
					<td class="context"><pi:formatDate value="${quotation.applyTime}" pattern="yyyy/MM/dd" /></td>
				</tr>
				<tr>
					<td class="title">供應商：</td>
					<td class="context"><pi:formatJsonCode type="supplierName" value="${quotation.supplierId}" /></td>
					<td class="title">商品名稱：</td>
					<td class="context"><c:out value="${quotation.itemName}"/></td>
					<td class="title">商品條碼：</td>
					<td class="context"><c:out value="${quotation.barcode1}"/></td>
				</tr>
			</c:when>
			
		</c:choose>
		
	</table>
	<div class="auditStatusWrap">
		<hr>
		<ul>
			<li class="startPoint">供應商</li>
			<li class="flowRight">&nbsp;</li>
			<c:forEach items="${flowStageList}" var="item" varStatus="st">				
				<c:if test="${item.TRACK_ID == trackId}">
					<c:set var="position" value="${st.count}"/>
				</c:if>
			</c:forEach>
			
			<c:if test='${(flowType != "ec" && quotation.flowStatus == "Y") || (flowType == "ec" && quotation.flowStatusEc == "Y")}'>
				<c:set var="position" value="99999"/>
			</c:if>
				
			<c:forEach items="${flowStageList}" var="item" varStatus="st">
				<li class="checkpointWrap">
					<div>
						<ul>
							<li>
								<div class="roleWrap ${item.APPROVE_STATUS != null && st.count <= position ? 'auditComplete' : ''}">
									<c:out value="${item.ADUIT_NAME}"/>
								</div>
								<c:choose>
								  	<c:when test="${item.APPROVE_STATUS != null && st.count <= position}">
								    	<div class="pointNum green_<c:out value="${item.SORT_INDEX}"/>">&nbsp;</div>
								  	</c:when>
								  	<c:otherwise>
								    	<div class="pointNum gray_<c:out value="${item.SORT_INDEX}"/>">&nbsp;</div>
								  	</c:otherwise>
								</c:choose>
							</li>
							<li class="verifyStatus">
							<c:choose>
								<c:when test="${flowType=='outStock' || flowType=='support' || flowType=='pallet' }">
									<c:choose>
										<c:when test='${item.TRACK_ID == trackId && appealState !="Y"}'>
									    	審核中中
									  	</c:when>
									  	<c:when test="${item.APPROVE_STATUS != null && st.count <= position}">
									    	<pi:formatJsonCode type="${flowType}Status" value="${item.APPROVE_STATUS}" /><br>
									  	</c:when>
									  	<c:otherwise>
									  		
									  	</c:otherwise>
							  		</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test='${item.TRACK_ID == trackId && (flowType != "ec" && quotation.flowStatus != "Y") || (flowType == "ec" && quotation.flowStatusEc != "Y")}'>
									    	審核中
									  	</c:when>
									  	<c:when test="${item.APPROVE_STATUS != null && st.count < position}">
									    	<pi:formatJsonCode type="${flowType}Status" value="${item.APPROVE_STATUS}" /><br>
									  	</c:when>
									  	<c:otherwise>
									  		
									  	</c:otherwise>
								  	</c:choose>
								</c:otherwise>
							  </c:choose>
							</li>
							<li class="verifyDate">
								<c:if test="${item.APPROVE_STATUS != null && st.count <= position}">
									<pi:formatDate value="${item.UPDATE_TIME}" pattern="yyyy/MM/dd" />
								</c:if>
							</li>
							<li class="agreeReason"></li>
							<li class="rejectReason"></li>
						</ul>
					</div>
				</li>
				<li class="flowRight">&nbsp;</li>
			</c:forEach>
			<li class='endPoint endPointYes' id="endPoint">結束</li>
		</ul>
		
		<div style="height:220px"  class="displayview">
			<table class="its" id="disList">
				<thead>
					<tr>
						<th class="sortable table-xs">
							送達時間
						</th>
						<th class="sortable">
							關卡順序
						</th>
						<th class="sortable">
							審核角色
						</th>
						<th class="sortable">
							審核人員
						</th>
						<th class="sortable">
							審核結果
						</th>
						<th class="sortable table-xs">
							審核意見
						</th>
						<c:if test="${flowType == 'outStock'}">
							<th class="sortable">
								調整罰款金額
							</th>
						</c:if>
						<c:if test="${flowType == 'pallet' || flowType == 'support'}">
							<th class="sortable">
								調整收費金額
							</th>
						</c:if>					
						<th class="sortable table-xs">
							審核時間
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${flowTrackList}" var="item" varStatus="st">
						<tr class='${st.index%2 == 0 ? "odd" : "even"}'>
							<td class="center"><pi:formatDate value="${item.CREATE_TIME}" pattern="yyyy/MM/dd hh:mm" /></td>
							<td class="center"><c:out value="${item.SORT_INDEX}"/></td>
							<td class=""><pi:formatJsonCode type="cmdUserRoleNo" value="${item.AUDIT_USER_ROLE}" /></td>
							<td class=""><pi:formatJsonCode type="userName" value="${item.AUDIT_USER}" /></td>
							<td class="">
								<c:choose>
								  	<c:when test="${item.SORT_INDEX != null}">
								    	<pi:formatJsonCode type="${flowType}Status" value="${item.APPROVE_STATUS}" />
								  	</c:when>
								  	<c:otherwise><!-- stageId為null，無flowstage資料 -->
								    	退回供應商
								  	</c:otherwise>
								</c:choose>
							</td>
							<td class=""><c:out value="${item.AUDIT_OPINION}"/></td>
							<c:if test="${flowType == 'outStock' || flowType == 'pallet' || flowType == 'support'}">
								<td>
									<c:out value="${item.OUT_STUCK_AMT }" />
								</td>
							</c:if>
							<td class="center table-xs"><pi:formatDate value="${item.UPDATE_TIME}" pattern="yyyy/MM/dd hh:mm" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>