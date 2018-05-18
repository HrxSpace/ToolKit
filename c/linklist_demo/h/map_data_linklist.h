/********************************************************************************
**
** 文件名:     map_data_linklist.h
** 版权所有:   (c) 2014-2020 厦门雅迅网络股份有限公司
** 文件描述:      双向循环链表类
**
*********************************************************************************
**             修改历史记录
**===============================================================================
**| 日期       | 作者   |  修改记录
**===============================================================================
**| 2018/05/15 | 黄睿欣 |  创建该文件
*********************************************************************************/
#ifndef _H_MAP_DATA_LINKLIST_
#define _H_MAP_DATA_LINKLIST_
		
#ifdef __cplusplus
		extern "C"
		{
#endif

#include "types.h"

typedef struct data_node{
	INT8U node_type;                        /* 链表标识，用以区分不同链表 */
    void *data;                             /* 数据域 */
	INT32U size_data;                       /* 数据域大小 */
	struct data_node *prv;                  /* 指向上一个链表的指针 */  
    struct data_node *next;                 /* 指向下一个链表的指针 */
} DATA_NODE_L;        /* 数据链表结构 */

typedef struct {
	INT16U       item_num;                  /* 节点的个数 */
    DATA_NODE_L *head;                      /* 链表头 */
    DATA_NODE_L *tail;                      /* 链表尾 */
} DATA_LIST_L;        /* 数据链表结构 */

typedef struct data {
	INT8U arg1;
	INT16U arg2;
	INT32U arg3;
} data_t;        /* 测试结构 */

/* 初始化链表 */
BOOLEAN link_init(DATA_LIST_L *lp);

/* 在链表尾上追加一个节点 */
BOOLEAN append_tail_node(DATA_LIST_L *lp, void *bp);

/* 删除指定节点 */
void del_node(DATA_LIST_L *lp, void *bp);

/* 删除链表尾节点 */
void del_tail_node(DATA_LIST_L *lp);

/* 将指定节点之前节点删除，包含该节点 */
void del_all_prv_node(DATA_LIST_L *lp, void *bp);

/* 获取第no个节点 */
void * get_node(DATA_LIST_L *lp, INT16U no);

/* 创建一个空节点 */
void * create_node();


#ifdef __cplusplus
	}
#endif
	
#endif    /* end of _H_MAP_DATA_LINKLIST_ */
/* 将数据打包到节点 */
void node_pack(DATA_NODE_L *node, void *pdata, INT32U size_pdata, INT8U link_sign);

