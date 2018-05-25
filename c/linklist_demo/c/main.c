
#include <stdio.h>
#include <string.h>
#include <unistd.h>

#include "types.h"
#include "map_data_linklist.h"

static DATA_LIST_L lp;

typedef struct {
	INT64S arg1;
	INT64S arg2;
	INT64S arg3;
} data_t;

int main(int argv, char **argc)
{
	long long i = 0;
	DATA_NODE_L *t,*temp;
	data_t *data, *d;
		
	link_init(&lp);                /* 初始化链表 */
	while(i < 1000){
		data = (data_t *)malloc(sizeof(data_t));
		t = create_node();
		data->arg1 = i * 10;
		data->arg2 = i * 1000;
		data->arg3 = i * 100000;
		node_pack(t, data, sizeof(data_t));
		append_tail_node(&lp, t);
		i++;
	}
	printf("%d\n", lp.item_num);
	t = lp.head;
	for(i = 0; i < lp.item_num ; i++){
		printf("i = %lld, %p, %p, %p\n", i, t->prv, t, t->next);
		if(PNULL != t->next){
			t = t->next;
		}
	}

	for(i = 0; i < lp.item_num ; i++){
		temp = (DATA_NODE_L *)get_node(&lp, i);
		d = (data_t *)temp->data;
		printf("for1 : i = %lld, temp = %p, arg1 = %lld, arg2 = %lld, arg3 = %lld\n", i, temp, d->arg1, d->arg2, d->arg3);
	}
	
	del_tail_node(&lp);
	
	printf("3\n");
	
	for(i = 0; i < lp.item_num ; i++){
		temp = (DATA_NODE_L *)get_node(&lp, i);
		d = (data_t *)temp->data;
		printf("for2 : i = %lld, temp = %p, arg1 = %lld, arg2 = %lld, arg3 = %lld\n", i, temp, d->arg1, d->arg2, d->arg3);
	}

	printf("4\n");
	temp = (DATA_NODE_L *)get_node(&lp, 20);
	del_all_prv_node(&lp, temp);
	printf("5\n");

	for(i = 0; i < lp.item_num ; i++){
		temp = (DATA_NODE_L *)get_node(&lp, i);
		d = (data_t *)temp->data;
		printf("for3 : i = %lld, temp = %p, arg1 = %lld, arg2 = %lld, arg3 = %lld\n", i, temp, d->arg1, d->arg2, d->arg3);
	}

	printf("lp.item_num = %d, lp.head = %p, lp.tail = %p\n", lp.item_num, lp.head, lp.tail);
	printf("6\n");

	return 0;
}
