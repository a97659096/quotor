package com.quotorcloud.quotor.academy.log.aspect;

import com.alibaba.fastjson.JSONObject;
import com.quotorcloud.quotor.academy.api.dto.log.ColumnComment;
import com.quotorcloud.quotor.academy.api.entity.log.LogRecord;
import com.quotorcloud.quotor.academy.log.annotation.OperationLog;
import com.quotorcloud.quotor.academy.log.enums.OperationType;
import com.quotorcloud.quotor.academy.mapper.log.LogRecordMapper;
import com.quotorcloud.quotor.academy.service.log.LogRecordService;
import com.quotorcloud.quotor.common.core.util.ComUtil;
import com.quotorcloud.quotor.common.security.service.QuotorUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Aspect
@Component
public class OperationLogAop {

	@Autowired
	private LogRecordMapper operationLogMapper;

	@Autowired
	private LogRecordService logRecordService;

	@Autowired
	private TransactionTemplate txTemplate;

	@Around(value = "@annotation(operationlog)")
	public void logAround(final ProceedingJoinPoint p, final OperationLog operationlog) throws Throwable {
		OperationType type = operationlog.type();
		if (OperationType.UPDATE.equals(type)) {
			update(p, operationlog);
		}
		if (OperationType.ADD.equals(type)) {
			add(p, operationlog);
		}
		if (OperationType.DELETE.equals(type)) {
			delete(p, operationlog);
		}
	}

	public void delete(final ProceedingJoinPoint p, final OperationLog operationlog) {
		txTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				StringBuilder sql = new StringBuilder();
                int contentType = operationlog.contentType();
                String logName = operationlog.name();
				Object[] args = p.getArgs();
                QuotorUser quotorUser = (QuotorUser) args[operationlog.operatorRef()];
				String logTable = operationlog.table();
				String idFiled = operationlog.idField();
				if (operationlog.idRef()==-1) {
					throw new RuntimeException();
				}
				String id = args[operationlog.idRef()].toString();
				String[] cloum = operationlog.cloum();

				sql.append("SELECT ");
				for (int i = 0; i < cloum.length; i++) {
					if (i == 0) {
						sql.append("`" + cloum[i] + "` ");
					} else {
						sql.append(",`" + cloum[i] + "` ");
					}
				}
				sql.append(" FROM " + logTable + " WHERE "+ idFiled + " = "+ "'" + id +"'");
				Map<String, Object> oldMap = operationLogMapper.selectAnyTalbe(sql.toString());
				try {
					p.proceed();
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}

				if (oldMap!=null) {
					List<LogRecord> opds = new ArrayList<LogRecord>();
					for (String clm : cloum) {
						Object oldclm = oldMap.get(clm);
						LogRecord opd = new LogRecord();
						opd.setOpeartionEvent("删除了:"+oldclm);
                        opd.setName(oldclm.toString());
                        opd.setType(contentType);
                        opd.setOpeartionType(logName);
                        opd.setOpeartionUser(quotorUser.getName());
                        opd.setShopId(String.valueOf(quotorUser.getDeptId()));
                        opd.setShopName(quotorUser.getDeptName());
						opds.add(opd);
					}
					if (!opds.isEmpty()) {
						logRecordService.saveBatch(opds);
					}
				}
			}
		});
	}

	private void add(final ProceedingJoinPoint p, final OperationLog operationlog) {
		txTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

                String logName = operationlog.name();
                int contentType = operationlog.contentType();
				Object[] args = p.getArgs();
                QuotorUser quotorUser = (QuotorUser) args[operationlog.operatorRef()];
				String[] cloum = operationlog.cloum();

				try {
					p.proceed();
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
				JSONObject  jsonObject = JSONObject.parseObject(JSONObject.toJSONString(args[operationlog.operatorObj()])) ;
					List<LogRecord> opds = new ArrayList<LogRecord>();
					for (String clm : cloum) {
						LogRecord opd = new LogRecord();
						opd.setOpeartionEvent("创建了:" + jsonObject.get(clm));
						opd.setName((String) jsonObject.get(clm));
						opd.setType(contentType);
						opd.setOpeartionType(logName);
						opd.setOpeartionUser(quotorUser.getName());
                        opd.setShopId(String.valueOf(quotorUser.getDeptId()));
                        opd.setShopName(quotorUser.getDeptName());
						opds.add(opd);
					}
					if (!opds.isEmpty()) {
						logRecordService.saveBatch(opds);
					}

			}
		});
	}

	public void update(final ProceedingJoinPoint p, final OperationLog operationlog) {
		txTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				StringBuilder sql = new StringBuilder();
                String logName = operationlog.name();
				int contentType = operationlog.contentType();
				String idField = operationlog.idField();
				Object[] args = p.getArgs();
				String logTable = operationlog.table();
				if (operationlog.idRef()==-1) {
					throw new RuntimeException();
				}
				String id = args[operationlog.idRef()].toString();
				String[] cloum = operationlog.cloum();
				QuotorUser quotorUser = (QuotorUser) args[operationlog.operatorRef()];

				Map<String, Object> columnCommentMap = new HashMap<String, Object>();
				List<ColumnComment> columnCommentList = operationLogMapper.selectColumnCommentByTable(logTable);

				for (ColumnComment cc : columnCommentList) {
					columnCommentMap.put(cc.getColumn(), cc.getComment());
				}
				if (cloum.length == 0) {
					Set<String> keySet = columnCommentMap.keySet();
					List<String> list = new ArrayList<String>();
					for (String o : keySet) {
						list.add(o.toString());
					}
					cloum = list.toArray(new String[list.size()]);
				}
				sql.append("SELECT ");
				for (int i = 0; i < cloum.length; i++) {
					if (i == 0) {
						sql.append("`" + cloum[i] + "` ");
					} else {
						sql.append(",`" + cloum[i] + "` ");
					}
				}
				sql.append(" FROM " + logTable + " WHERE " + idField + " = " + "'" + id + "'");
				Map<String, Object> oldMap = operationLogMapper.selectAnyTalbe(sql.toString());

				try {
					p.proceed();
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
				Map<String, Object> newMap = operationLogMapper.selectAnyTalbe(sql.toString());
				if (oldMap!=null&&newMap!=null) {
					List<LogRecord> opds = new ArrayList();
					for (String clm : cloum) {
						if(clm.contains("gmt_modified")){
							continue;
						}
						if(!ComUtil.isEmpty(oldMap.get(clm)) && !ComUtil.isEmpty(newMap.get(clm)) && !oldMap.get(clm).equals(newMap.get(clm))) {
							Object oldclm = oldMap.get(clm);
							Object newclm = newMap.get(clm);
							LogRecord opd = new LogRecord();
							opd.setOpeartionEvent("将" + columnCommentMap.get(clm).toString() + "由" + oldclm + "改为" + newclm.toString());
							opd.setName(newclm.toString());
							opd.setType(contentType);
							opd.setOpeartionUser(quotorUser.getName());
							opd.setOpeartionType(logName);
							opd.setShopId(String.valueOf(quotorUser.getDeptId()));
							opd.setShopName(quotorUser.getDeptName());
							opds.add(opd);
						}
					}
					if (!opds.isEmpty()) {
						logRecordService.saveBatch(opds);
					}
				}
			}
		});
	}
}
