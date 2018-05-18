/********************************************************************************
**
** 文件名:     map_data_linklist.c
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
#include <stdio.h>
#include <string.h>

#include "map_data_linklist.h"

/*******************************************************************
** 函数名:     link_init
** 函数描述:   初始化链表
** 参数:       [in]  lp:        链表
** 返回:       TRUE:    成功
**           FALSE:   失败
********************************************************************/
BOOLEAN link_init(DATA_LIST_L *lp)
{
    if (PNULL == lp) {
        return FALSE;
    }
    
    lp->head = PNULL;
    lp->tail = PNULL;
    lp->item_num = 0;
	
    return TRUE;
}

/*******************************************************************
** 函数名:     append_tail_node
** 函数描述:   在链表尾上追加一个节点
** 参数:       [in]  lp:        链表
**           [in]  bp:        待追加节点
** 返回:       TRUE:    成功
**           FALSE:   失败
********************************************************************/
BOOLEAN append_tail_node(DATA_LIST_L *lp, void *bp)
{
    DATA_NODE_L *cur_node;

    if (lp == 0 || bp == 0) {
        return FALSE;
    }

    cur_node = (DATA_NODE_L *)bp;
    cur_node->prv = lp->tail;
    if (0 == lp->item_num) {
        lp->head = cur_node;
    } else {
        lp->tail->next = cur_node;
    }
    cur_node->next = PNULL;
    lp->tail = cur_node;
    lp->item_num++;
	
    return TRUE;
}

/*******************************************************************
** 函数名:     del_node
** 函数描述:   删除指定节点
** 参数:       [in]  lp:        链表
**           [in]  bp:        指定节点
********************************************************************/
void del_node(DATA_LIST_L *lp, void *bp)
{
    DATA_NODE_L *cur_node, *prv_node, *next_node;
	data_t *d;

    if (PNULL == lp || PNULL == bp) {
		return;
	}
	
    if (lp->item_num == 0) {
		return;
	}

    lp->item_num--;
    cur_node  = (DATA_NODE_L *)bp;
	d = (data_t *)cur_node->data;
    prv_node  = cur_node->prv;
    next_node = cur_node->next;
	printf("cur_node = %p, prv_node = %p, next_node = %p\n", cur_node, prv_node, next_node);
	printf("cur_node->size_data = %d, cur_node->node_type = %d, cur_node->data = %p\n", 
		cur_node->size_data, cur_node->node_type, cur_node->data);
	printf("del --- arg1 = %d, arg2 = %d, arg3 = %d\n", d->arg1, d->arg2, d->arg3);
    if (PNULL == prv_node) {
        lp->head = next_node;
    } else {
        prv_node->next = next_node;
    }
    if (PNULL == next_node) {
        lp->tail = prv_node;
    } else {
        next_node->prv = prv_node;
    }

	FK_FREE(cur_node->data);
	FK_FREE(cur_node);
	printf("cur_node->size_data = %d, cur_node->node_type = %d, cur_node->data = %p\n", 
		cur_node->size_data, cur_node->node_type, cur_node->data);

	printf("del --- arg1 = %d, arg2 = %d, arg3 = %d\n", d->arg1, d->arg2, d->arg3);
}

/*******************************************************************
** 函数名:     del_head_node
** 函数描述:   删除链表头节点
** 参数:       [in]  lp:        链表
********************************************************************/
void del_head_node(DATA_LIST_L *lp)
{
    void *bp;

    if (PNULL == lp || 0 == lp->item_num) {
		return;
	}

    bp = (void *)lp->head;
    del_node(lp, bp);
}

/*******************************************************************
** 函数名:     del_tail_node
** 函数描述:   删除链表尾节点
** 参数:      [in]  lp:        链表
********************************************************************/
void del_tail_node(DATA_LIST_L *lp)
{
    void *bp;

    if (PNULL == lp || 0 == lp->item_num) {
		return;
	}

    bp = (void *)lp->tail;
	printf("tail = %p\n", bp);
    del_node(lp, bp);
}

/*******************************************************************
 ** 函数名: del_all_prv_node
 ** 函数描述: 将指定节点之前节点删除，包含该节点
 ** 参数: 	  [in]	lp: 	   链表
 ** 		  [in]	bp: 	   指定节点
 ********************************************************************/
void del_all_prv_node(DATA_LIST_L *lp, void *bp)
{
	DATA_NODE_L *cur_node, *prv_node;

	if (PNULL == lp || 0 == lp->item_num) {
		return;
	}

    cur_node  = (DATA_NODE_L *)bp;

	while(cur_node){
		prv_node  = cur_node->prv;
		del_node(lp, cur_node);
		cur_node = prv_node;
	}
}

/*******************************************************************
** 函数名:     get_node
** 函数描述:   获取第no个节点
** 参数:       [in]  lp:        链表
**           [in]  no:        待获取节点的序号, 注意: 序号从0开始编号, 0表示链表头节点
** 返回:       第no节点; 如返回0, 则表示第no个节点不存在
********************************************************************/
void * get_node(DATA_LIST_L *lp, INT16U no)
{
    DATA_NODE_L *cur_node;

    if (PNULL == lp || lp->item_num <= no) {
        return PNULL;
    }

    cur_node = lp->head;

	while(no > 0){
        if (PNULL == cur_node) {
            break;
        }
        cur_node = cur_node->next;
		no--;
	}

    if (PNULL == cur_node) {
        return PNULL;
    } else {
        return (void *)cur_node;
    }
}

/*******************************************************************
 ** 函数名: create_node
 ** 函数描述: 创建一个空节点
 ** 返回:     空节点
 ********************************************************************/
void * create_node()
{
	DATA_NODE_L *node;

	node = (DATA_NODE_L *)FK_MALLOC(sizeof(DATA_NODE_L));
	node->data = PNULL;
	node->node_type = 0;
	node->next = PNULL;
	node->prv = PNULL;
	node->size_data = 0;

	return (void *)node;
}

/*******************************************************************
 ** 函数名: node_pack
 ** 函数描述: 将数据打包到节点
 ** 参数: [in] pdata: 数据域
 **       [in] size_pdata: 数据大小
 **       [in] node_type:链表的标识
 ********************************************************************/
void node_pack(DATA_NODE_L *node ,void *pdata, INT32U size_pdata, INT8U node_type)
{
	data_t *data;

	if(PNULL == node){
		return;
	}

	node->node_type = node_type;
	node->size_data = size_pdata;
	node->data = FK_MALLOC(sizeof(size_pdata));
	node->next = PNULL;
	memcpy(node->data, pdata, size_pdata);

	data = (data_t *)pdata;
	printf("arg1 = %d, arg2 = %d, arg3 = %d\n", data->arg1, data->arg2, data->arg3);
}

