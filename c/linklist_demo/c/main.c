
#include <stdio.h>
#include <string.h>

#include "types.h"
#include "map_data_linklist.h"

static DATA_LIST_L lp;

int main(int argv, char **argc)
{
	
	int i;
	DATA_NODE_L *t,*temp;
	data_t *data, *d;
	
	printf("0\n");
		
	link_init(&lp);                /* 初始化链表 */
	printf("1\n");
	
	for(i = 1; i < 10; i++){
		data = (data_t *)malloc(sizeof(data_t));
		t = create_node();
		data->arg1 = i * 10;
		data->arg2 = i * 1000;
		data->arg3 = i * 100000;
		printf("i = %d,  t1 = %p\n", i, t);
		node_pack(t, data, sizeof(data_t), 15);
		printf("i = %d,  t2 = %p\n", i, t);
		d = (data_t *)t->data;
		printf("i = %d,  t3 = %p\n", i, t);
		append_tail_node(&lp, t);
		printf("i = %d,  t4 = %p\n", i, t);
	}
	printf("2\n");
	for(i = 0; i < lp.item_num ; i++){
		temp = (DATA_NODE_L *)get_node(&lp, i);
		printf("i = %d,  temp = %p\n", i, temp);
		d = (data_t *)temp->data;
		printf("arg1 = %d, arg2 = %d, arg3 = %d\n", d->arg1, d->arg2, d->arg3);
	}

	del_tail_node(&lp);
	
	printf("3\n");
	
	for(i = 0; i < lp.item_num ; i++){
		temp = (DATA_NODE_L *)get_node(&lp, i);
		d = (data_t *)temp->data;
		printf("arg1 = %d, arg2 = %d, arg3 = %d\n", d->arg1, d->arg2, d->arg3);
	}

	printf("4\n");
	temp = (DATA_NODE_L *)get_node(&lp, 4);
	del_all_prv_node(&lp, temp);
	printf("5\n");

	for(i = 0; i < lp.item_num ; i++){
		temp = (DATA_NODE_L *)get_node(&lp, i);
		d = (data_t *)temp->data;
		printf("arg1 = %d, arg2 = %d, arg3 = %d\n", d->arg1, d->arg2, d->arg3);
	}

	printf("lp.item_num = %d, lp.head = %p, lp.tail = %p\n", lp.item_num, lp.head, lp.tail);
	printf("6\n");

	return 0;
}
