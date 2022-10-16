import { Component, OnInit } from '@angular/core';

import { Todo } from '../todo';
import { TodoService } from '../todo.service';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.css']
})
export class TodosComponent implements OnInit {
  todos: Todo[] = [];

  constructor(private todoService: TodoService) { }

  ngOnInit(): void {
    this.getTodos();
  }

  getTodos(): void {
    this.todoService.getTodos()
    .subscribe(todos => this.todos = todos);
  }

  add(summary: string, description: string): void {
    if (!summary || !description) { return; }
    this.todoService.addTodo({summary: summary, description: description} as Todo)
      .subscribe(hero => {
        this.todos.push(hero);
      });
  }

  delete(hero: Todo): void {
    this.todos = this.todos.filter(h => h !== hero);
    this.todoService.deleteTodo(hero.id).subscribe();
  }

  refresh(): void {
    this.todoService.getTodos()
      .subscribe(todos => this.todos = todos);
  }
}
