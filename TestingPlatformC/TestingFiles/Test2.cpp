#include <dlfcn.h>
#include <assert.h>
#include <dirent.h>
#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <map>

using namespace std;
//链表节点
struct Node;
typedef struct Node* PNode;

struct Node
{
	int docNum;
	PNode next;
};
typedef struct Node* DocNode;

vector<DocNode> copy(vector<DocNode> nodes);

//创建节点
DocNode createDocNode(int value)
{
	vector<DocNode> nodes;
	cout<<nodes.size()<<endl;

	DocNode node = (DocNode)malloc(sizeof(struct Node));
	//DocNode* node = new DocNode();
	if(node!=NULL)
	{
		node->next = NULL;
		node->docNum = value;
	}
	else cout<<"Out of space!\n"<<endl;
	return node;
}

//查找节点是否存在 -1 不存在 1 存在
int valueExist(DocNode node,int docNum)
{
	DocNode x = node;
	while(x!=NULL && x->docNum != docNum)
	{
		x = x->next;
	}
	if(x==NULL) return -1;
	else return 1;
}

//根据一个值获取一个节点,同时这个节点会被原节点列表中移除
DocNode getNode(DocNode  node, int docNum)
{
	//node有可能为空!
	if(node == NULL) return NULL;
	DocNode pre = node;
	DocNode x = node;
	while(x!=NULL && x->docNum != docNum)
	{
		x = x->next;
		//第一次pre不移动
		//从第二次开始,pre移动
		if(pre->next!=x)
		{
			pre= pre->next;
		}
	}
	//没有找到结果
	if(x==NULL) return NULL;
	else
	{	
		//找到结果有三种情况:
		//1.如果是头结点并且没有下一个节点了(此链表只有一个节点)
		if(x==node && x->next == NULL)
		{
			return x;
		}
		//2.这种情况是x是头结点,并且包含下一个
		else if(x==node && x->next!=NULL)
		{
			pre = x->next;
			x->next = NULL;
			return x;
		}
		else
		{			
			//3.找到结果,将节点断开出来
			pre->next = x->next;
			x->next = NULL;
			return x;
		}
	}
}


//因为所有操作均是建立在链表的操作基础之上,所以必须把链表结果拷贝一份,以防止破坏原有的结构
vector<DocNode> copy(vector<DocNode> nodes)
{
	vector<DocNode> result;
	cout<<"nodes.size()="<<nodes.size()<<"Ok"<<endl;
	for(int i =0; i <nodes.size(); i++ )
	{
		//拷贝头节点
		DocNode head = nodes[i];		
		cout<<"nodes["<<i<<"]->docNum = "<<head->docNum<<" OK!"<<endl;
		DocNode node = createDocNode(head->docNum);
		cout<<"Copy head "<<i<<" OK!"<<endl;
		//准备好要移动的指针
		DocNode temp = node;
		DocNode probe = head->next;
		//开始遍历
		while(probe!=NULL)
		{
			temp->next =  createDocNode(probe->docNum);
			temp = temp->next;
			probe = probe->next;
		}
		//推入结果列表
		result.push_back(node);
	}
	cout<<"result.size()="<<result.size()<<"Ok"<<endl;
	return result;
}




//显示一个链表
void displayDocList(DocNode list)
{
	DocNode node = list;
	while(node->next!=NULL)
	{
		cout<<node->docNum<<" ";
		node=node->next;
	}
	cout<<node->docNum<<endl;
}


int main(int argc , char** argv)
{
	cout<<"Copy Test"<<endl;
	vector<DocNode> nodes;
	
	DocNode node1=createDocNode(1);
	DocNode node2=createDocNode(2);
	DocNode node3=createDocNode(3);
	DocNode node4=createDocNode(4);
	DocNode node5=createDocNode(5);
	DocNode node6=createDocNode(6);
	DocNode node7=createDocNode(7);
	DocNode node8=createDocNode(8);

	node1->next = node2;
	node2->next = node3;
	node3->next = node4;

	node5->next = node6;
	node6->next = node7;
	node7->next = node8;

	nodes.push_back(node1);
	nodes.push_back(node5);
	
	vector<DocNode> newnode = copy(nodes);

	cout<<newnode.size()<<endl;
	displayDocList(newnode[0]);
	displayDocList(newnode[1]);
	
	node6->next = NULL;

	displayDocList(nodes[0]);
	displayDocList(nodes[1]);

	displayDocList(newnode[0]);
	displayDocList(newnode[1]);

	return 0;
}
