/********************************************************************************
**
** 文件名:     types.h
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
#ifndef _H_TYPES_
#define _H_TYPES_
		
#ifdef __cplusplus
		extern "C"
		{
#endif

#include <malloc.h>

#define FK_MALLOC               malloc
#define FK_FREE                 free
#define PNULL                   0

#define FALSE                   0
#define TRUE                    1
/*
********************************************************************************
* 数据类型定义
********************************************************************************
*/
typedef unsigned char               BOOLEAN;                    /* 布尔类型 */
typedef unsigned char               INT8U;                      /* 8位无符号整形数 */
typedef signed   char               INT8S;                      /* 8位有符号整形数 */
typedef unsigned short              INT16U;                     /* 16位无符号整形数 */
typedef signed   short              INT16S;                     /* 16位有符号整形数 */
typedef unsigned int                INT32U;                     /* 32位无符号整形数 */
typedef signed   int                INT32S;                     /* 32位有符号整形数 */
typedef unsigned long long          INT64U;                     /* 64位无符号整形数 */
typedef signed   long long          INT64S;                     /* 64位有符号整形数 */
typedef float                       FP32;                       /* 32位浮点数 */
typedef double                      FP64;                       /* 64位浮点数 */

#ifdef __cplusplus
	}
#endif
	
#endif    /* end of _H_TYPES_ */
